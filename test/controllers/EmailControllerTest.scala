package controllers

import Services.{MailerService, MongoService}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class EmailControllerTest extends PlaySpec with Results with MockitoSugar {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  val mongoService = mock[MongoService]
  val emailService = mock[MailerService]

  "Page# Email" should {
    "contain some text" in {
      val controller = new EmailController(Helpers.stubControllerComponents(), mongoService, emailService)
      val result: Future[Result] = controller.email().apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Your Email Address")
      contentAsString(result) must include("Subject")
      contentAsString(result) must include("Submit")
    }
  }

  "Page# subEmail" should {
    "contain some text" in {
      val controller = new EmailController(Helpers.stubControllerComponents(), mongoService, emailService)
      val result: Future[Result] = controller.subEmail().apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("From Email")
      contentAsString(result) must include("Subject")
      contentAsString(result) must include("Send Email To Subscribers")
    }
  }

}
