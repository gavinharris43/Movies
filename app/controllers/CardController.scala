package controllers

import javax.inject._
import models.{CardDetails}
import play.api.mvc._
@Singleton
class CardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {


  def checkout(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.checkout(CardDetails.CardForm))
  }

}