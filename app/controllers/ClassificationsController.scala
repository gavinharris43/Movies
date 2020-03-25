package controllers

import javax.inject._
import play.api.i18n.MessagesProvider
import play.api.mvc._

class ClassificationsController @Inject()(cc: ControllerComponents) extends AbstractController(cc)
 with play.api.i18n.I18nSupport {

  def classifications: Action[AnyContent] = Action {
    Ok(views.html.classifications())
  }
}
