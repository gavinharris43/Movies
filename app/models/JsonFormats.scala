package models

object JsonFormats {
  import play.api.libs.json.Json
  import play.api.libs.json.OFormat

  implicit val bsonObjectIDFormat: OFormat[Bookings] = Json.format[Bookings]
  implicit val moviesFormat: OFormat[Movies] = Json.format[Movies]
  implicit val placesFormat: OFormat[Places] = Json.format[Places]
}
