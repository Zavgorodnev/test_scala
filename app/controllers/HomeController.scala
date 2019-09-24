package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.{Author, Book, BoorAuthorGenForm}
import play.api.Logging
import services._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                               templateAuthor: views.html.author,
                               authorService: AuthorService,
                               bookService: BookService,
                               bookAuthorService: BookAuthorService) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def authorList(): Action[AnyContent] = Action.async  { implicit request: Request[AnyContent] =>
    authorService.listAllAuthors map { authors =>
      Ok(views.html.author(authors))
    }
  }

  def addBook() = Action.async { implicit request: Request[AnyContent] =>
    BoorAuthorGenForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => {
        Future.successful(Ok(views.html.index(errorForm, Seq.empty[Book])))
      },
      data => {
        val newBook = Book(0, data.title, data.year)
        val newAuthor = Author(0, data.author_name)
        bookAuthorService.addBookAuthor(newBook, newAuthor).map( _ => Redirect(routes.HomeController.index()))
      })
  }

  def deleteBook(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    bookService.deleteBook(id) map { res =>
      Redirect(routes.HomeController.index())
    }
  }

}
