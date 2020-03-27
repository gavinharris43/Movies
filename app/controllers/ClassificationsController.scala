package controllers

import javax.inject.Inject
import models.Rating
import play.api.db
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import play.api.libs.json._
import reactivemongo.play.json._
import reactivemongo.play.json.JsObjectDocumentWriter
import reactivemongo.play.json.collection._

import scala.concurrent.{ExecutionContext, Future}
import models.JsonFormats._

class ClassificationsController @Inject()(cc: ControllerComponents,
                                          val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc)
   with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {

  def classifications: Action[AnyContent] = Action {
    Ok(views.html.classifications())
  }

  implicit def ec: ExecutionContext = cc.executionContext
  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("ratings"))

  def insertRatings(): Action[AnyContent] = Action.async {
    val futureResult = collection.flatMap(_.insert.many(Seq(Rating.ratingU,
                                                            Rating.ratingPG,
                                                            Rating.rating12,
                                                            Rating.rating15,
                                                            Rating.rating18)))
    futureResult.map(_ => Ok("Inserted"))
  }



}
