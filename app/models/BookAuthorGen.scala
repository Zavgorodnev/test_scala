package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

case class BookAuthorGen(title: String, year: String, author_name: String)

case class BoorAuthorGenFormData(title: String, year: String, author_name: String)

object BoorAuthorGenForm{

  val form = Form(
    mapping(
      "title"       -> nonEmptyText,
      "year"        -> nonEmptyText,
      "author_name" -> nonEmptyText,
    )(BoorAuthorGenFormData.apply)(BoorAuthorGenFormData.unapply)
  )
}

import slick.jdbc.MySQLProfile.api._
