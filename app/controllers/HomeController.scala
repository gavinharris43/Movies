package controllers

import javax.inject._
import play.api.i18n.{I18nSupport, MessagesApi, MessagesProvider}
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

}
