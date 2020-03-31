package controllers

import akka.http.scaladsl.model.DateTime
import javax.inject._
import models.{FilmDate, Movies, Review}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class DiscussionBoardController @Inject()(cc: ControllerComponents, val movieController: MovieController, val reviewController: ReviewController) extends AbstractController(cc)  with play.api.i18n.I18nSupport  {

  def discussionIndex = Action.async {
    movieController.findAll().map(movies => Ok(views.html.discussionBoard(movies)))
  }

  def discussionListingIndex(title:String) = Action.async {
    implicit request: Request[AnyContent] =>
      movieController.findByName(title).flatMap{film => reviewController.findByTitleMovieReview(film.head.movieTitle).map{reviews =>
        Ok(views.html.discussionListing(film.head, reviews, Review.ReviewForm.fill(Review("","","",film.head.movieTitle)))).withSession(request.session + ("film" -> film.head.movieTitle))
      }}

  }



}
