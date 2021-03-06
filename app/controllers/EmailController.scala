package controllers

import Services.{MailerService, MongoService}
import javax.inject.Inject
import models.JsonFormats._
import models.{EMail, Subscribe}
import play.api.libs.json.Json
import play.api.mvc._
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import play.api.libs.mailer._

import scala.concurrent.{ExecutionContext, Future}

class EmailController @Inject()(components: ControllerComponents,
                                val mongoService: MongoService,
                                val mailerService: MailerService
                               ) extends AbstractController(components)
  with play.api.i18n.I18nSupport {

  implicit val ec: ExecutionContext = components.executionContext
  val collection: Future[JSONCollection] = mongoService.mailCollection

  def subEmail: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.emailSubs(EMail.mailForm))
  }

  def subSubmit: Action[AnyContent] = Action.async { implicit request =>
    EMail.mailForm.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.emailSubs(formWithErrors)))
      },
      mailData => {
        findAllSubscribers().map({
          subscribers =>
            val email = Email(
              s"${mailData.subject}",
              s"Kinoplex <${mailData.email}>",
              subscribers.map(subscriber => subscriber.email),
              bodyText = Some(mailData.body),
              bodyHtml = Some(s"""<html><body><p> ${mailData.body}</p></body></html>""")
            )
            Ok(s"Sent ${mailerService.sendEmail(email)}")
        })
      }
    )
  }

  def findAllSubscribers(): Future[List[Subscribe]] = {
    collection.map {
      _.find(Json.obj("contactable" -> true))
        .sort(Json.obj("created" -> -1))
        .cursor[Subscribe]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Subscribe]]()
      )
    )
  }

  def email: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.email(EMail.mailForm))
  }

  def submit: Action[AnyContent] = Action.async { implicit request =>
    EMail.mailForm.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.email(formWithErrors)))
      },
      mailData => {
        val email = Email(
          s"${mailData.subject}",
          s"Kinoplex <${mailData.email}>",
          Seq("tq01244@gmail.com"),
          bodyText = Some(mailData.body),
          bodyHtml = Some(s"""<html><body><p>FROM ${mailData.email} ${mailData.body}</p></body></html>""")
        )
        Future(Ok(s"Sent ${mailerService.sendEmail(email)}"))
      })
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
