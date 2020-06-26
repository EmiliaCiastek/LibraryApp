package com.ciastek.library.user.books.view

import com.ciastek.library.common.books.BookModel

interface UserBooksMVP {

    interface Presenter {

        fun attach(view: View)

        fun detach()
    }

    interface View {

        fun showBooks(books: List<BookModel>)

        fun showError(message: String?)
    }
}
