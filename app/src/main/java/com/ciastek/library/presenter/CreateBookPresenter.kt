package com.ciastek.library.presenter

import com.ciastek.library.CreateBookContract
import com.ciastek.library.isValid
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.BookDao

class CreateBookPresenter(private val booksDao: BookDao) : CreateBookContract.Presenter {

    private var view: CreateBookContract.View? = null

    override fun saveBookButtonClicked(book: Book) {
        if (book.isValid()) {
            booksDao.addBook(book)
            view?.setBookCreated(book)
        } else
            view?.showError()
    }

    override fun attachView(view: CreateBookContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}