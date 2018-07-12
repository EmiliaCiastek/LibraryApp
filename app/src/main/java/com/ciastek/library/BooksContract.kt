package com.ciastek.library

import com.ciastek.library.model.Book

object BooksContracts {
    interface Presenter : Attachable<BooksContracts.View> {
        fun loadData()
    }

    interface View {
        fun addBook(book: Book)

        fun setBooks(books: List<Book>)
    }
}