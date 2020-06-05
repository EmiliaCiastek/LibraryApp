package com.ciastek.library.remote.repository

import com.ciastek.library.remote.Author
import com.ciastek.library.remote.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteBooksRepository: RemoteBooksRepository {

    private val fakeBooks = listOf<Pair<Book, Author>>(
            Book("Wicked", 1L, 1L) to Author("Gregory", "Maguire", 1L),
            Book("Looking for Alaska", 2L, 2L) to Author("John", "Green", 2L))


    override fun getBooks(): Flow<Pair<Book, Author>> = flow {
        fakeBooks.forEach {
            emit(it)
        }
    }
}