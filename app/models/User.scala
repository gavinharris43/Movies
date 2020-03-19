package models

import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID
import play.api.data.Form
import play.api.data.Forms._

case class User(
                 _id: BSONObjectID,
                 age: Int,
                 firstName: String,
                 lastName: String,
                 feeds: List[Feed])

case class Feed(
                 name: String,
                 url: String)

object User {
  def apply(age: Int,
            firstName: String,
            lastName: String,
            feeds: List[Feed]) = new User(BSONObjectID.generate(), age, firstName, lastName, feeds)

  def unapply(user: User): Option[(Int, String, String, List[Feed])] = Some(user.age, user.firstName, user.lastName, user.feeds)

  val userForm: Form[User] = Form(
    mapping(
      "age" -> number,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "feeds" -> list(
        mapping(
          "name" -> nonEmptyText,
          "url" -> nonEmptyText
        )(Feed.apply)(Feed.unapply)
      )
    )(User.apply)(User.unapply)
  )
}

object JsonFormats {

  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val feedFormat: OFormat[Feed] = Json.format[Feed]
  implicit val userFormat: OFormat[User] = Json.format[User]
  implicit val loginDetailsFormat: OFormat[LoginDetails] = Json.format[LoginDetails]
}
