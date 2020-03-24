package models

import akka.http.scaladsl.model.DateTime
import play.api.data.Form
import play.api.data.Forms._

case class Bookings(title: String, dateTime: String, name: String, concession :Int, adult:Int, child:Int)

object Bookings {

  val bookings: Form[Bookings] = Form(
    mapping(
      "movieTitle" -> nonEmptyText,
      "dateTime " -> nonEmptyText,
      "bookerName"-> nonEmptyText,
      "concession" ->  number(min = 0, max = 120),
      "adult"->  number(min = 0, max = 120),
      "child" -> number(min = 0, max = 120)
    )(Bookings.apply)(Bookings.unapply)
  )

}


