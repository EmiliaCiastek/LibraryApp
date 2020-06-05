package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.Author
import com.ciastek.library.remote.model.Book
import com.ciastek.library.remote.model.BookWithAuthor
import io.reactivex.Observable
import io.reactivex.Observable.error
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

    override fun getBooks2(): Observable<List<BookWithAuthor>> =
            error(NotImplementedError())
}
