package models

import play.api.data.Form
import play.api.data.Forms._

import scala.collection.mutable.ListBuffer

case class CardDetails(nameoncard: String, cardnumber: String, expirydate:String, securitycode:String, name:String, address:String , city: String, county: String, country: String, postcode: String,
                       phone:Long, email:String)

object CardDetails {

  val CardForm: Form[CardDetails] = Form(
    mapping(
      "nameoncard" -> nonEmptyText,
      "cardnumber" -> nonEmptyText,
      "expirydate" -> nonEmptyText,
      "securitycode" -> nonEmptyText,
      "name" -> text.verifying("Name Required", _.nonEmpty),
      "address" -> text.verifying("Address Required", _.nonEmpty),
      "city" -> text.verifying("City Required", _.nonEmpty),
      "county" -> text.verifying("County Required", _.nonEmpty),
      "country" -> text.verifying("Country Required", _.nonEmpty),
      "postcode" -> text.verifying("Post Code Required", _.nonEmpty),
      "phone" -> longNumber,
      "email" -> text.verifying("E-Mail Required", _.nonEmpty)

    )(CardDetails.apply)(CardDetails.unapply)
  )



}
