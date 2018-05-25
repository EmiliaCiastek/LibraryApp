package com.ciastek.library.model

class InMemoryBookDao : BookDao {
    private val books: List<Book> = listOf(
            Book("Effective Java", "J. Bloch"),
            Book("Kotlin in Action", "D. Jemerov")
    )

    override fun addBook(book: Book) {
        books + book
    }

    override fun getBooks(): List<Book> = books
}