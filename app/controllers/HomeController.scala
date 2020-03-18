package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val mongoService: MongoService) extends AbstractController(cc) {

  def index: Action[AnyContent] = authAction {
    Ok(views.html.index("Your new application is ready."))
  }

  def showRecords(): Action[AnyContent] = Action.async {
    mongoService.findAll().map( listOfUsers =>
      Ok(listOfUsers.toString())
    )
  }

}
