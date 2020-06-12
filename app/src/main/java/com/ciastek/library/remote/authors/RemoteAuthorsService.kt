package com.ciastek.library.remote.authors

import com.ciastek.library.remote.authors.details.reporitory.AuthorDetails
import com.ciastek.library.remote.authors.list.repository.Author
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteAuthorsService {

    @GET("/authors")
    fun getAuthors(): Single<List<Author>>

    @GET("/authors/{id}")
    fun getAuthor(@Path("id") authorId: Long): Single<AuthorDetails>
}