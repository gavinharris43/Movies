package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class ScreensController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def screenType = Action {
    Ok(views.html.screens())
  }

}
