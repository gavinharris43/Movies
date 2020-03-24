package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport{

  def index = Action { implicit Action =>
    Ok(views.html.index("Your new application is ready."))
  }



}
