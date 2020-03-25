package controllers

import javax.inject.{Inject, Singleton}
import models.JsonFormats._
import models.Bookings
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TicketBookingController @Inject()(components: ControllerComponents,
                                        val reactiveMongoApi: ReactiveMongoApi
                                       ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("bookings"))


  def index: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.bookings(Bookings.bookingForm))
  }

  def submit: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Bookings.bookingForm.bindFromRequest.fold({
      formWithErrors =>
        Future.successful(BadRequest(views.html.bookings(formWithErrors)))
    }, {
      booking =>
          val cursor: Future[Cursor[Bookings]] = collection.map {
            _.find(Json.obj("title" -> booking.movieTitle, "dateTime" -> booking.dateTime)).
              sort(Json.obj("created" -> -1)).
              cursor[Bookings]()
          }

          val futureUsersList: Future[List[Bookings]] =
            cursor.flatMap(
              _.collect[List](
                -1,
                Cursor.FailOnError[List[Bookings]]()
              )
            )

          futureUsersList.map {
            value =>
              if (value.headOption.nonEmpty) {
                collection.flatMap(_.insert.one(booking)).map {
                  _ => Ok("booked")
                }
                Ok("booked")
              }
              else BadRequest("No Movie/Viewing found for date and time/ movie selected")
          }
    })
  }


}

