package models

import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID



object JsonFormats {
  import play.api.libs.json.Json
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._
  implicit val bsonObjectIDFormat: OFormat[Bookings] = Json.format[Bookings]
  implicit val moviesFormat: OFormat[Movies] = Json.format[Movies]
  implicit val cardFormat: OFormat[CardDetails] = Json.format[CardDetails]
  implicit val reviewFormat: OFormat[Review] = Json.format[Review]
  implicit val bsonObjectIDFormats: OFormat[BSONObjectID] = Json.format[BSONObjectID]
}
