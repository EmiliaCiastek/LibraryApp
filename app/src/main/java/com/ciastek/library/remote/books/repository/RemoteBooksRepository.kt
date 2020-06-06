package com.ciastek.library.remote.books.repository

import io.reactivex.Single

interface RemoteBooksRepository {

    fun getBooks(): Single<List<Book>>
}
