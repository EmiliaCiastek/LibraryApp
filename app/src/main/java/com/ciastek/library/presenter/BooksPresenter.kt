package com.ciastek.library.presenter

import com.ciastek.library.BooksContracts
import com.ciastek.library.model.Book
import com.ciastek.library.model.BookDao

class BooksPresenter(private val booksDao: BookDao) : BooksContracts.Presenter {
    private var view: BooksContracts.View? = null

    override fun newBookReceived(book: Book) {
        view?.addBook(book)
        booksDao.addBook(book)
    }

    override fun attachView(view: BooksContracts.View) {
        this.view = view
        view.setBooks(booksDao.getBooks())
    }

    override fun detachView() {
        this.view = null
    }

    override fun newBookButtonClicked() {
        view?.startNewBookActivity()
    }
}