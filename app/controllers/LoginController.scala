package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login() = TODO

  def loginSubmit() = TODO

}
