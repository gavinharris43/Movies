package models

import play.api.libs.json.OFormat



object JsonFormats {
  import play.api.libs.json.Json
  implicit val bsonObjectIDFormat: OFormat[Bookings] = Json.format[Bookings]
  implicit val moviesFormat: OFormat[Movies] = Json.format[Movies]
  implicit val placesFormat: OFormat[Places] = Json.format[Places]
}
