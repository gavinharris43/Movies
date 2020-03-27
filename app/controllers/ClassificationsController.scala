package controllers

import javax.inject.Inject
import models.Rating
import play.api.db
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}


class ClassificationsController @Inject()(cc: ControllerComponents,
                                          val reactiveMongoApi: ReactiveMongoApi) extends AbstractController(cc)
   with MongoController with ReactiveMongoComponents {

  def classifications: Action[AnyContent] = Action {
    Ok(views.html.classifications())
  }

  implicit def ec: ExecutionContext = cc.executionContext
  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("ratings"))

//  def insertRatings() = Action.async { implicit request: Request[AnyContent] =>
//
//    val futureResult = collection.insert(Rating.ratings.ratingU)
//    futureResult.map(_ => Ok)
//  }



}
