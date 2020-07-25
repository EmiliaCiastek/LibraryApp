package com.ciastek.library.remote.books

import com.ciastek.library.remote.books.add.NewBook
import com.ciastek.library.remote.books.details.reporitory.BookDetails
import com.ciastek.library.remote.books.list.repository.Book
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RemoteBooksService {

    @GET("books")
    fun getBooks(): Single<List<Book>>

    @GET("books/{id}")
    fun getBook(@Path("id") id: Long): Single<BookDetails>

    @POST("books")
    fun addBook(@Body book: NewBook): Completable
}
