package com.ciastek.library

import com.ciastek.library.model.Book

object BooksContracts {

    interface Presenter : Attachable<BooksContracts.View> {
        fun newBookButtonClicked()

        fun newBookReceived(book: Book)
    }

    interface View {
        fun startNewBookActivity()

        fun addBook(book: Book)
    }
}