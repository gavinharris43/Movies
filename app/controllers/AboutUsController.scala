package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class AboutUsController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def about: Action[AnyContent] = Action {
    Ok(views.html.aboutus())
  }
}
