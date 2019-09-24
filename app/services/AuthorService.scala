package services

import com.google.inject.Inject
import models.{Author, Authors}

import scala.concurrent.Future

class AuthorService @Inject()(authors: Authors){
  def addUAuthor(author: Author): Future[String] = {
    authors.add(author)
  }

  def getAuthor(id: Long): Future[Option[Author]] = {
    authors.get(id)
  }

  def listAllAuthors: Future[Seq[Author]] = {
    authors.listAll
  }
}
