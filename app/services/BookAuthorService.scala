package services

import com.google.inject.Inject
import models.{Author, Authors, Book, BookAuthor, Books, BooksAuthors}

import scala.concurrent.Future

class BookAuthorService @Inject()(booksAuthors: BooksAuthors,
                                  books: Books,
                                  authors: Authors,
                                  authorService: AuthorService,
                                  bookService: BookService) {

  def addBookAuthor(book: Book, author: Author): Future[String] = {
    //TODO
    books.add(book);
    authors.add(author);
  }

  def listAllUsers: Future[Seq[BookAuthor]] = {
    booksAuthors.listAll
  }
}

