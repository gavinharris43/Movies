package forms

import play.api.data.Form
import play.api.data.Forms._

class CardDetails {


  val form = Form(
    mapping(
      "name on card" -> nonEmptyText,
      "expiry date" -> nonEmptyText,
      "site" -> nonEmptyText,
      "city" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  case class Data(
                   name: String,
                   about: String,
                   site: String,
                   city: String)

}