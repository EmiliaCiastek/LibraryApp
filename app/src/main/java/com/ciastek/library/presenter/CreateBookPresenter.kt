package com.ciastek.library.presenter

import com.ciastek.library.CreateBookContract
import com.ciastek.library.model.Book

class CreateBookPresenter : CreateBookContract.Presenter {

    private var view: CreateBookContract.View? = null

    override fun saveBookButtonClicked(book: Book) {
        view?.setBookCreated(book) //TODO: add validation
    }

    override fun attachView(view: CreateBookContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}