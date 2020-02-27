package controllers

import javax.inject.{Inject, Singleton}
import models.LoginDetails
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login(LoginDetails.loginForm))
  }

  def loginSubmit() = Action { implicit request: Request[AnyContent] =>
    LoginDetails.loginForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest(views.html.login(formWithErrors))
    }, { loginDetails =>
      if (LoginDetails.checkIfUserIsVali(loginDetails))
        Redirect(routes.HomeController.index())
      else
        BadRequest("Incorrect username or password")
    })
  }

}
