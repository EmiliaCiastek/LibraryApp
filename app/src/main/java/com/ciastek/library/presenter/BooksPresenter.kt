package com.ciastek.library.presenter

import com.ciastek.library.BooksContracts
import com.ciastek.library.model.db.BookDao

class BooksPresenter(private val booksDao: BookDao) : BooksContracts.Presenter {
    private var view: BooksContracts.View? = null

    override fun attachView(view: BooksContracts.View) {
        this.view = view
        view.setBooks(booksDao.getBooks())
    }

    override fun detachView() {
        this.view = null
    }
}