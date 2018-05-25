package com.ciastek.library.presenter

import com.ciastek.library.BooksContracts
import com.ciastek.library.model.Book

class BooksPresenter : BooksContracts.Presenter {
    private var view: BooksContracts.View? = null

    override fun newBookReceived(book: Book) {
        view?.addBook(book)
    }

    override fun attachView(view: BooksContracts.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun newBookButtonClicked() {
        view?.startNewBookActivity()
    }

}