package controllers
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{CardDetails, FilmDate, Movies, Review}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global



class ReviewController @Inject()(components: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {


  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("movies"))

  def createReview: Action[AnyContent] = Action.async {

    implicit request: Request[AnyContent] =>
      CardDetails.CardForm.bindFromRequest.fold({ formWithErrors =>
        Future.successful(BadRequest(views.html.checkout(formWithErrors)))
      }, { cards =>
        collection.flatMap(_.insert.one(cards)).map{_ => Ok("Booked")}
      })


  }
}
