package controllers

import javax.inject._
import models.Bookings
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, _}

import scala.concurrent.Future
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}


@Singleton
class TicketBookingController @Inject()(components: ControllerComponents,
                                        val reactiveMongoApi: ReactiveMongoApi
                                       ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponent


def index = Action {
  Ok (views.html.bookings (Bookings.bookings) )
}

  def submit () = Action.async {
  implicit request: Request[AnyContent] =>
  Bookings.bookings.bindFromRequest.fold ( {
  formWithErrors =>
  Future.successful (BadRequest (views.html.signup (formWithErrors) ) )
}, {
  person =>

  val cursor: Future[Cursor[Bookings]] = collection.map {
  _.find (Json.obj ("username" -> person.username) ).
  sort (Json.obj ("created" -> - 1) ).
  cursor[Bookings] ()
}

  val futureUsersList: Future[List[Bookings]] =
  cursor.flatMap (
  _.collect[List] (
  - 1,
  Cursor.FailOnError[List[Bookings]] ()
  )
  )

  futureUsersList.map {
  value =>
  if (value.headOption.isEmpty) {
  collection.flatMap (_.insert.one (person) ).map {
  _ => Ok (request2Messages.messages ("accountCreated") )
}
  Ok (request2Messages.messages ("accountCreated") )
}
  else BadRequest (request2Messages.messages ("usernameInUser") )
}
})
}

}


}
