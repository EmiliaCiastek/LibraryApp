package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.Author
import com.ciastek.library.remote.model.Book
import com.ciastek.library.remote.model.BookWithAuthor
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteBooksService {

    @GET("books")
    suspend fun getBooks(): List<Book>

    @GET("authors/{id}")
    suspend fun getAuthor(@Path("id") id: Long): Author

    fun getBooks2(): Observable<BookWithAuthor>
}