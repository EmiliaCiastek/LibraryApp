package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.Author
import com.ciastek.library.remote.model.Book
import com.ciastek.library.remote.model.BookWithAuthor
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface RemoteBooksRepository {

    fun getBooks(): Flow<Pair<Book, Author>>

    fun getBooks2(): Observable<List<BookWithAuthor>>
}
