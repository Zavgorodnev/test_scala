package models

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

case class Book(book_id: Long, title: String, year: String)

import slick.jdbc.MySQLProfile.api._

class BookTable(tag: Tag) extends Table[Book](tag, "book") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def title = column[String]("title")
  def year = column[String]("year")

  override def * =
    (id, title, year) <>(Book.tupled, Book.unapply)
}

class Books @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  val books = TableQuery[BookTable]

  def add(book: Book): Future[String] = {
    dbConfig.db.run(books += book).map(res => "uccessfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(books.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[Book]] = {
    dbConfig.db.run(books.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[Book]] = {
    dbConfig.db.run(books.result)
  }

}