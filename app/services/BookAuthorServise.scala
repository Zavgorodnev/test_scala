package services

import com.google.inject.Inject
import models.{BookAuthor, BooksAuthors}

import scala.concurrent.Future

class BookAuthorServise @Inject() (booksAuthors: BooksAuthors) {

  def addBookAuthor(bookAuthor: BookAuthor): Future[String] = {
    booksAuthors.add(bookAuthor)
  }

  def listAllUsers: Future[Seq[BookAuthor]] = {
    booksAuthors.listAll
  }
}

