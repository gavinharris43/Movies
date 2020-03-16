package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {

  def index: Action[AnyContent] = authAction {
    Ok(views.html.index("Your new application is ready."))
  }

}
