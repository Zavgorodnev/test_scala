package services

import com.google.inject.Inject
import models.{Author, Book, BookAuthor, BooksAuthors}

import scala.concurrent.Future

class BookAuthorService @Inject()(booksAuthors: BooksAuthors) {

  def addBookAuthor(book: Book, author: Author): Future[String] = {
    //TODO
    booksAuthors.add(bookAuthor)
  }

  def listAllUsers: Future[Seq[BookAuthor]] = {
    booksAuthors.listAll
  }
}

