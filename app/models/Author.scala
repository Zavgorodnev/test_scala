package models

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

case class Author(author_id: Long, author_name: String)

import slick.jdbc.MySQLProfile.api._

class AuthorTable(tag: Tag) extends Table[Author](tag, "author") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")

  override def * =
    (id, name) <> (Author.tupled, Author.unapply)
}

class Authors @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  val authors = TableQuery[AuthorTable]

  def add(author: Author): Future[String] = {
    dbConfig.db.run(authors += author).map(res => "successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def get(id: Long): Future[Option[Author]] = {
    dbConfig.db.run(authors.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[Author]] = {
    dbConfig.db.run(authors.result)
  }
}