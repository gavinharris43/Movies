package controllers

import javax.inject.Inject
import models.JsonFormats._
import models.Rating
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.play.json.JsObjectDocumentWriter
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.{ExecutionContext, Future}

class ClassificationsController @Inject()(cc: ControllerComponents,
                                          val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc)
    with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("ratings"))

  def create: Action[AnyContent] = Action.async {
    val ratings = Rating.ratings
    val futureResult = collection.flatMap(_.insert.many(ratings))
    futureResult.map(_ => Ok("Ratings inserted"))
  }

  def findAll: Future[List[Rating]] = {
    collection.map {
      _.find(Json.obj())
        .cursor[Rating]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Rating]]()
      )
    )
  }

  def classificationIndex: Action[AnyContent] = Action.async {
    findAll.map(ratings => Ok(views.html.classifications(ratings)))
  }
}
