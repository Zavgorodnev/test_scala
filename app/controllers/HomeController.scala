package controllers

import javax.inject._
import play.api._
import play.api.Logging
import play.api.mvc._
import models.Author
import services.AuthorService


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject()(cc: ControllerComponents, authorService: AuthorService, templateAuthors: views.html.author) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def authorList(): Action[AnyContent] = Action.async  { implicit request: Request[AnyContent] =>
    authorService.listAllAuthors map { authors =>
      Ok(views.html.author(authors))
    }

  }

}
