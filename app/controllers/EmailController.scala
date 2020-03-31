package controllers

import Services.MongoService
import javax.inject.Inject
import models.JsonFormats._
import models.{Mail, Subscribe}
import play.api.libs.json.Json
import play.api.mvc._
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import com.typesafe.play._
import play.api._
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class EmailController @Inject()(components: ControllerComponents,
                                val mongoService: MongoService
                                     ) extends AbstractController(components)
   with play.api.i18n.I18nSupport {

  implicit val ec: ExecutionContext = components.executionContext
  val collection: Future[JSONCollection] = mongoService.mailCollection

  def email: Action[AnyContent] = Action{ implicit request: Request[AnyContent]=>
    Ok(views.html.email(Mail.mailForm))
  }

  def submit: Action[AnyContent] = Action { implicit request =>
    Mail.mailForm.bindFromRequest.fold (
      formWithErrors => {
        Redirect("/")
      },
      mailData => {
        val mail = use[MailerPlugin].email
        mail.setSubject("Email sent using Scala")
        mail.addRecipient(mailData.email)
        mail.addFrom(mailData.email)
        mail.send("Hello World")
        Redirect("/")
      })
  }


  def findAllMail(): Future[List[Mail]] = {
    collection.map {
      _.find(Json.obj("contactable" -> true))
        .sort(Json.obj("created" -> -1))
        .cursor[Mail]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Mail]]()
      )
    )
  }

  def getAllContactableEmails: Action[AnyContent] = Action.async {
    findAllMail().map {
      mailList => Ok("Subscriptions: " + mailList.toString())


    }
  }

  def subscribe: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.subscribe(Subscribe.subscribeForm))
  }

  def subscribeSubmit: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Subscribe.subscribeForm.bindFromRequest.fold({
      formWithErrors =>
        Future.successful(BadRequest(views.html.subscribe(formWithErrors)))
    }, {
      subscribe =>
        val cursor: Future[Cursor[Subscribe]] = collection.map {
          _.find(Json.obj("email" -> subscribe.email)).
            sort(Json.obj("created" -> -1)).
            cursor[Subscribe]()
        }

        val futureUsersList: Future[List[Subscribe]] =
          cursor.flatMap(
            _.collect[List](
              -1,
              Cursor.FailOnError[List[Subscribe]]()
            )
          )

        futureUsersList.map {
          value =>
            if (value.headOption.isEmpty) {
              collection.flatMap(_.insert.one(subscribe)).map {
                _ => Ok("subscribed")
              }
              Ok("subscribed")
            }
            else BadRequest("subscription not added: email already exists")
        }
    })
  }
}
