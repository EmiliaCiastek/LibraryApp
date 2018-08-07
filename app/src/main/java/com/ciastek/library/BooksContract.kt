package com.ciastek.library

import com.ciastek.library.model.Book

interface BooksContract {
    interface Presenter : Attachable<BooksContract.View>

    interface View {
        fun addBook(book: Book)

        fun setBooks(books: List<Book>)
    }
}