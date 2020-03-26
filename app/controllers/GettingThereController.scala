package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class GettingThereController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def gettingThere: Action[AnyContent] = Action {
    Ok(views.html.classifications())
  }
}