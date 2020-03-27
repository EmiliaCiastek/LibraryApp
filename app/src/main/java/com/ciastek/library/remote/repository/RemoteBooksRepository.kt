package com.ciastek.library.remote.repository

import com.ciastek.library.remote.Author
import com.ciastek.library.remote.Book
import kotlinx.coroutines.flow.Flow

interface RemoteBooksRepository {

    fun getBooks(): Flow<Pair<Book, Author>>
}
