package com.ciastek.library.model

interface BookDao {
    fun addBook(book: Book)

    fun getBooks(): List<Book>
}