package com.ciastek.library

import com.ciastek.library.model.Book

object CreateBookContract {
    interface Presenter : Attachable<CreateBookContract.View> {
        fun saveBookButtonClicked(book: Book)
    }

    interface View {
        fun showError()

        fun setBookCreated(book: Book)
    }
}