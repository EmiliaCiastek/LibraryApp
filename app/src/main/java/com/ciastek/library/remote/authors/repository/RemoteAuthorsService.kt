package com.ciastek.library.remote.authors.repository

import io.reactivex.Single
import retrofit2.http.GET

interface RemoteAuthorsService {

    @GET("/authors")
    fun getAuthors(): Single<List<Author>>
}