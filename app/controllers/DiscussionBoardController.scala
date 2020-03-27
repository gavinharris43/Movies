package controllers

import akka.http.scaladsl.model.DateTime
import javax.inject._
import models.{FilmDate, Movies, Review}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class DiscussionBoardController @Inject()(cc: ControllerComponents, val movieController: MovieController) extends AbstractController(cc) {

  def discussionIndex = Action.async {
    movieController.findAll().map(movies => Ok(views.html.discussionBoard(movies)))
  }

  def discussionListingIndex(title:String) = Action.async {
    implicit request: Request[AnyContent] =>
    movieController.findByName(title).map(film => Ok(views.html.discussionListing(film.head,Review.ReviewForm)))
  }



}
