package com.ciastek.library.presenter

import com.ciastek.library.CreateBookContract
import com.ciastek.library.isValid
import com.ciastek.library.model.Book

class CreateBookPresenter : CreateBookContract.Presenter {

    private var view: CreateBookContract.View? = null

    override fun saveBookButtonClicked(book: Book) {
        if (book.isValid())
            view?.setBookCreated(book)
        else
            view?.showError()
    }

    override fun attachView(view: CreateBookContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}