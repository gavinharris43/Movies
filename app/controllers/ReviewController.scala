package controllers
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{Await, ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{CardDetails, FilmDate, Movies, Review}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}



class ReviewController @Inject()(components: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi, val movieController: MovieController) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {


  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("reviews"))

  def createReview: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      Review.ReviewForm.bindFromRequest.fold({ formWithErrors =>
        movieController.findByName(request.session.get("film").getOrElse("")).flatMap{ movie => findByTitleMovieReview(movie.head.movieTitle).map{reviews =>
          BadRequest(views.html.discussionListing(movie.head, reviews, formWithErrors))
        }}
      }, {  review =>
        collection.flatMap(_.insert.one(review)).flatMap{
          _ => movieController.findByName(request.session.get("film").getOrElse("")).flatMap{ movie => findByTitleMovieReview(movie.head.movieTitle).map(reviews =>
            Ok(views.html.discussionListing(movie.head, reviews, Review.ReviewForm.fill(Review("","","",movie.head.movieTitle)))).withSession(request.session + ("film" -> movie.head.movieTitle)))}        }
      })


  }

  def findAllMovieReview(): Future[List[Review]] = {
    collection.map {
      _.find(Json.obj())
        .sort(Json.obj())
        .cursor[Review]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Review]]()
      )
    )
  }

  def findByTitleMovieReview(title:String): Future[List[Review]] = {
    collection.map {
      _.find(Json.obj("movieTitle" -> title))
        .sort(Json.obj())
        .cursor[Review]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Review]]()
      )
    )
  }
}
