package models

import play.api.libs.json.OFormat

object JsonFormats {

  import play.api.libs.json.Json

  implicit val bsonObjectIDFormat: OFormat[Bookings] = Json.format[Bookings]
}