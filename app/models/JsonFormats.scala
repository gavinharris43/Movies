package models

import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID



object JsonFormats {
  import play.api.libs.json.Json
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._
  implicit val bsonObjectIDFormat: OFormat[Bookings] = Json.format[Bookings]
  implicit val ratingFormat: OFormat[Rating] = Json.format[Rating]
  implicit val moviesFormat: OFormat[Movies] = Json.format[Movies]
  implicit val cardFormat: OFormat[CardDetails] = Json.format[CardDetails]
  implicit val bsonObjectIDFormats: OFormat[BSONObjectID] = Json.format[BSONObjectID]
  implicit val placesFormat: OFormat[Place] = Json.format[Place]
  implicit val mailFormat: OFormat[EMail] = Json.format[EMail]
  implicit val subscribeFormat: OFormat[Subscribe] = Json.format[Subscribe]

}
