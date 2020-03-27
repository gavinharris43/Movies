package controllers


import javax.inject.Inject
import models.JsonFormats._
import models.{CardDetails}
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.{ExecutionContext, Future}


class CardDetailsController @Inject()(components: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {


  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("website"))

  def hardcode: Action[AnyContent] = Action.async {
    val user = CardDetails(BSONObjectID.generate,"Nathan", "j", "j", "j", "j", "j", "j", "j", "j", "j", "j", "j")
    val futureResult = collection.flatMap(_.insert.one(user))
    futureResult.map(_=>Ok("Inserted"))
  }

  def createPayment: Action[AnyContent] = Action.async {

    implicit request: Request[AnyContent] =>
    CardDetails.CardForm.bindFromRequest.fold({ formWithErrors =>
     Future.successful(BadRequest(views.html.checkout(formWithErrors)))
    }, { cards =>
      collection.flatMap(_.insert.one(cards)).map{_ => Ok("Booked")}
    })


  }


}