package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

import scala.collection.mutable.ListBuffer

case class CardDetails(_id: BSONObjectID,
                       NameOnCard: String,
                       CardNumber: String,
                       ExpiryDate: String,
                       SecurityCode: String,
                       FullName: String,
                       Address: String,
                       City: String,
                       County: String,
                       Country: String,
                       Postcode: String,
                       Phone: String,
                       Email: String,
                      ) {
  val id: BSONObjectID = _id

}

object CardDetails {

  def apply(CardNumber: String, NameOnCard: String, ExpiryDate: String, SecurityCode: String, FullName: String, Address: String, City: String, County: String, Country: String, Postcode: String, Phone: String, Email: String) =
    new CardDetails(BSONObjectID.generate(), CardNumber, NameOnCard, ExpiryDate, SecurityCode, FullName, Address, City, County, Country, Postcode, Phone, Email)

  def unapply(arg: CardDetails): Option[( String, String, String, String, String, String, String, String, String, String, String, String)] =
    Option((arg.NameOnCard, arg.CardNumber, arg.ExpiryDate, arg.SecurityCode, arg.FullName, arg.Address, arg.City, arg.County, arg.Country, arg.Postcode, arg.Phone, arg.Email))


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
      "phone" -> text.verifying("Phone Number Required", _.nonEmpty),
      "email" -> text.verifying("E-Mail Required", _.nonEmpty)

    )(CardDetails.apply)(CardDetails.unapply)
  )

}