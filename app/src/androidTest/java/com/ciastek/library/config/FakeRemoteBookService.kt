package com.ciastek.library.config

import com.ciastek.library.remote.books.RemoteBooksService
import com.ciastek.library.remote.books.details.reporitory.BookDetails
import com.ciastek.library.remote.books.list.repository.Book
import io.reactivex.Single

class FakeRemoteBookService : RemoteBooksService {

    private val mockBooks = listOf(
            BookDetails(1L, "Effective Java", "Joshua Bloch", 4.5, "", "Best book about Java ever")
    )

    override fun getBooks(): Single<List<Book>> =
            Single.just(
                    mockBooks.map {
                        Book(it.title, it.author, it.id)
                    })

    override fun getBook(id: Long): Single<BookDetails> =
            Single.just(
                    mockBooks.find { it.id == id }
                            ?: BookDetails.empty()
            )
}
