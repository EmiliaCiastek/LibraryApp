package com.ciastek.library.remote.books.list.repository

import io.reactivex.Single
import retrofit2.http.GET

interface RemoteBooksService {

    @GET("books")
    fun getBooks(): Single<List<Book>>
}