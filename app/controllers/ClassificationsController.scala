package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class ClassificationsController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def classifications: Action[AnyContent] = Action {
    Ok(views.html.classifications())
  }
}
