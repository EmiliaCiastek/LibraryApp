package com.ciastek.library.remote.books.repository

import io.reactivex.Single

interface BooksRepository {

    fun getBooks(): Single<List<Book>>
}
