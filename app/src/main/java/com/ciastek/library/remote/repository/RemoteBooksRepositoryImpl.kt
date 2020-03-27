package com.ciastek.library.remote.repository

import com.ciastek.library.remote.Author
import com.ciastek.library.remote.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteBooksRepositoryImpl(private val booksService: RemoteBooksService) : RemoteBooksRepository {

    override fun getBooks(): Flow<Pair<Book, Author>> = flow {
        booksService.getBooks()
                .forEach { book ->
                    emit(Pair(book, booksService.getAuthor(book.authorId)))
                }
    }
            .flowOn(Dispatchers.IO)
}
