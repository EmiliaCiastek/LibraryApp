package com.ciastek.library

import com.ciastek.library.model.Book

interface CreateBookContract {
    interface Presenter : Attachable<CreateBookContract.View> {
        fun saveBookButtonClicked(book: Book)
    }

    interface View {
        fun showError()

        fun setBookCreated()
    }
}