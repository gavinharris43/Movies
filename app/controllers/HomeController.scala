package controllers

import helpers.FilmDateControl
import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global



@Singleton
class HomeController @Inject()(cc: ControllerComponents, movieController: MovieController, filmDateControl: FilmDateControl) extends AbstractController(cc) with play.api.i18n.I18nSupport{

  def index = Action.async {
    movieController.findAll().map(movies => Ok(views.html.index(movies.sortWith(filmDateControl.sortByDateTime(_,_)).take(3),"Your new application is ready.")) )

  }


  def openings: Action[AnyContent] = Action{
    Ok(views.html.openingtimes())
  }

}
