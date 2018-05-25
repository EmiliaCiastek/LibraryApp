package com.ciastek.library

import com.ciastek.library.model.Book

object BooksContracts {

    interface Presenter : Attachable<BooksContracts.View> {
        fun newBookButtonClicked()

        fun loadData()
    }

    interface View {
        fun startNewBookActivity()

        fun addBook(book: Book)

        fun setBooks(books: List<Book>)
    }
}