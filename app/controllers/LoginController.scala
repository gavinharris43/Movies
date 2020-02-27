package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login())
  }

  def loginSubmit() = TODO

}
