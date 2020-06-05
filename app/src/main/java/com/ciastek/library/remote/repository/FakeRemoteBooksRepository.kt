package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.Author
import com.ciastek.library.remote.model.Book
import com.ciastek.library.remote.model.BookWithAuthor
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteBooksRepository: RemoteBooksRepository {

    private val fakeBooks = listOf<Pair<Book, Author>>(
            Book("Wicked",
                                                                     1L,
                                                                     1L) to Author("Gregory",
                                                                                   "Maguire",
                                                                                   1L),
            Book("Looking for Alaska",
                                                                     2L,
                                                                     2L) to Author(
                    "John",
                    "Green",
                    2L))


    override fun getBooks(): Flow<Pair<Book, Author>> = flow {
        fakeBooks.forEach {
            emit(it)
        }
    }

    override fun getBooks2(): Observable<List<BookWithAuthor>> =
            Observable.error(NotImplementedError())
}