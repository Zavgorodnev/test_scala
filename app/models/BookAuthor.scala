package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import models.Book
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

case class BookAuthor(book_id: Long, author_id: Long)

import slick.jdbc.MySQLProfile.api._

class BookAuthorTable(tag: Tag) extends Table[BookAuthor](tag, "book_author") {
  val books = TableQuery[BookTable]
  val authors = TableQuery[AuthorTable]
  def bookId = column[Long]("book_id")
  def authorId = column[Long]("author_id")

  // ForeignKey

  def book = foreignKey("book_fk", bookId, books)(_.id, onDelete = ForeignKeyAction.Cascade)
  def author = foreignKey("author_fk", authorId, authors)(_.id, onDelete = ForeignKeyAction.Cascade)


  override def * =
    (bookId, authorId) <> (BookAuthor.tupled, BookAuthor.unapply)
}

class BooksAuthors @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  val books_authors = TableQuery[BookAuthorTable]

  def add(book_author: BookAuthor): Future[String] = {
    dbConfig.db.run(books_authors += book_author).map(res => "successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(users.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[User]] = {
    dbConfig.db.run(users.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[User]] = {
    dbConfig.db.run(users.result)
  }

}
