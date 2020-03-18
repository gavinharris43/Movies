package models

import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

object User {
  def apply(age: Int,
            firstName: String,
            lastName: String,
            feeds: List[Feed]) = new User(BSONObjectID.generate(), age, firstName, lastName, feeds)
}

case class User(
                 _id: BSONObjectID,
                 age: Int,
                 firstName: String,
                 lastName: String,
                 feeds: List[Feed])

case class Feed(
                 name: String,
                 url: String)

object JsonFormats {

  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val feedFormat: OFormat[Feed] = Json.format[Feed]
  implicit val userFormat: OFormat[User] = Json.format[User]
  implicit val loginDetailsFormat: OFormat[LoginDetails] = Json.format[LoginDetails]
}
