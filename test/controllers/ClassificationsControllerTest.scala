package controllers

import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future

class ClassificationsControllerTest extends PlaySpec with Results {
  "Classification controller" should {
    "produce text" in {
      val controller = new ClassificationsController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.classifications().apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("General viewing, but some scenes may be unsuitable for")
    }
  }
}
