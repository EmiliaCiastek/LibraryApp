package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.BookWithAuthor
import io.reactivex.Single
import retrofit2.http.GET

interface RemoteBooksService {

    @GET("books")
    fun getBooks(): Single<List<BookWithAuthor>>
}
