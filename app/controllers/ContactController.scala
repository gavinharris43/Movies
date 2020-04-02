package controllers

import com.google.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class ContactController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def contact: Action[AnyContent] = Action {
    Ok(views.html.contact())
  }
}
