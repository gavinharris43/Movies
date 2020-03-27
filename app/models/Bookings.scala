package models

import play.api.data.Form
import play.api.data.Forms._

case class Bookings(movieTitle: String, dateTime: String, bookerName: String, concession: Int, adult: Int, child: Int){
}

object Bookings {

  def apply(movieTitle: String, dateTime: String, bookerName: String, tickets:(Int, Int, Int)): Bookings =
    new Bookings(movieTitle, dateTime, bookerName, tickets._1, tickets._2, tickets._3)

  def unapply(arg: Bookings): Option[(String, String, String,(Int, Int, Int))] =
    Option(arg.movieTitle,arg.dateTime,arg.bookerName, (arg.concession,arg.adult,arg.child))

  val bookingForm: Form[Bookings] = Form(
    mapping(
      "movieTitle" -> nonEmptyText,
      "dateTime" -> nonEmptyText,
      "bookerName" -> nonEmptyText,
      "tickets" -> tuple(
      "concession" -> number(min = 0, max = 120),
      "adult" -> number(min = 0, max = 120),
      "child" -> number(min = 0, max = 120)
      ).verifying("No Tickets Ordered", tickets => (tickets._1 + tickets._2 + tickets._3) > 0)
  ) (Bookings.apply)(Bookings.unapply)
  )

}


