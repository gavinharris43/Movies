package models

import play.api.libs.json.OFormat

case class User(
                 age: Int,
                 firstName: String,
                 lastName: String,
                 feeds: List[Feed])

case class Feed(
                 name: String,
                 url: String)

object JsonFormats {
  import play.api.libs.json.Json

  implicit val feedFormat: OFormat[Feed] = Json.format[Feed]
  implicit val userFormat: OFormat[User] = Json.format[User]
}
