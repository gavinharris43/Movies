package controllers

import Services.MongoService
import akka.actor.ActorRefFactory
import akka.stream.ActorMaterializer
import models.Place
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar

class PlaceToGoControllerTest extends PlaySpec with Results with MockitoSugar {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  "Page#placess" should {
    "should be valid" in {
      val mongoService = mock[MongoService]
      val controller = new PlacesToGoController(Helpers.stubControllerComponents(), mongoService)
      val userList = Future {
        List[Place](
          Place("Bar", "bar Name", "2 alison way", "01918887777", "description ", "241", "url")
        )
      }
      when(mongoService.findAllPlaces()).thenReturn(userList)
      val result: Future[Result] = controller.getAllPlaces().apply(FakeRequest())
      contentType(result) mustBe Some("text/plain")
      userList.map {
        value => contentAsString(result) must be("Places: " + value.toString())
      }
    }
  }
  "Page#placeForm with template" should {
    "should be valid" in {
      val mongoService = mock[MongoService]
      val controller = new PlacesToGoController(Helpers.stubControllerComponents(), mongoService)
      val result: Future[Result] = controller.places.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Add Place")
    }
  }
  "Page#placesToGo with template" should {
    "should be valid" in {
      val mongoService = mock[MongoService]
      val controller = new PlacesToGoController(Helpers.stubControllerComponents(), mongoService)
      val placeList = Future {
        List[Place](
          Place("Bar", "bar Name", "2 alison way", "01918887777", "description ", "241", "url")
        )
      }
      when(mongoService.findAllPlaces()).thenReturn(placeList)
      val result: Future[Result] = controller.placesToGoIndex.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Places to Go!")
      contentAsString(result) must include("bar Name")
    }
  }
}



