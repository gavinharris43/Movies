package models

import play.api.data.Form
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

import play.api.data.Forms.{mapping, text}

case class Review(name: String, body: String, rating :String, movieTitle: String)

object Review{

  def apply(name: String,
            body: String,
            rating: String,
            movieTitle: String):
  Review = new Review(name, body, rating, movieTitle)



  val ReviewForm: Form[Review] = Form(
    mapping(
      "name" -> nonEmptyText,
      "body" -> nonEmptyText,
      "rating" -> nonEmptyText,
      "movieTitle" -> nonEmptyText,
    )(Review.apply)(Review.unapply)
  )
}
