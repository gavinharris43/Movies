package controllers

import javax.inject.Inject
import models.{Movies, Search}
import play.api.mvc._

import models.JsonFormats._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class SearchController @Inject()(components: ControllerComponents, val movieController: MovieController
                                ) extends AbstractController(components)
  with play.api.i18n.I18nSupport {


  def search(searchTerm: String): Future[List[Movies]] = {
    movieController.findAll().map { movies =>
      movies.filter(movie =>
        movie.movieTitle.toUpperCase.contains(searchTerm.toUpperCase))
    }
  }

  def index: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.search(Search.searchForm))
  }

  def submit: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Search.searchForm.bindFromRequest.fold({
      formWithErrors =>
        Future.successful(BadRequest(views.html.search(formWithErrors)))
    }, { form =>
      search(form.searchTerm).map(result => Ok(views.html.listingGallery(result)))
    })
  }


}
