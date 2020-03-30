package controllers

import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future

class GettingThereControllerTest extends PlaySpec with Results {
  "GettingThere page" should {
    "contain some text" in {
      val controller = new GettingThereController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.gettingThere().apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Limited customer parking is available in front of the building.")
    }
  }
}
