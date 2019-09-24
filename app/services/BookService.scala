package services

import com.google.inject.Inject
import models.{Book, Books}

import scala.concurrent.Future

class BookService @Inject() (books: Books) {

  def addBook(book: Book): Future[String] = {
    books.add(book)
  }

  def deleteBook(id: Long): Future[Int] = {
    books.delete(id)
  }

  def getBook(id: Long): Future[Option[Book]] = {
    books.get(id)
  }

  def listAllBooks: Future[Seq[Book]] = {
    books.listAll
  }
}