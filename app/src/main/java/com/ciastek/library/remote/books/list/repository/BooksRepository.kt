package com.ciastek.library.remote.books.list.repository

import io.reactivex.Single

interface BooksRepository {

    fun getBooks(): Single<List<Book>>
}
