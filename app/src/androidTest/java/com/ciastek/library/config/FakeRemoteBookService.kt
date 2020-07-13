package com.ciastek.library.config

import com.ciastek.library.remote.books.RemoteBooksService
import com.ciastek.library.remote.books.details.reporitory.BookDetails
import com.ciastek.library.remote.books.list.repository.Book
import io.reactivex.Single

class FakeRemoteBookService : RemoteBooksService {

    val mockBooks = listOf(
            BookDetails(1L, "Effective Java", "Joshua Bloch", 4.5, "", "Best book about Java ever"),
            BookDetails(2L, "Clean code", "Robert C. Martin", 5.0, "", ""),
            BookDetails(3L, "Clean coder", "Robert C. Martin", 4.0, "", ""),
            BookDetails(4L, "Looking for Alaska", "John Green", 5.0, "", ""),
            BookDetails(5L, "The fault in our stars", "John Green", 4.5, "", ""),
            BookDetails(6L, "To kill a mockingbird", "Harper Lee", 5.0, "", ""),
            BookDetails(7L, "The catcher in the rye", "J.D. Salinger", 2.0, "", ""),
            BookDetails(8L, "The bronze horseman", "Paullina Simons", 5.0, "", ""),
            BookDetails(9L, "The girl in the Times Square", "Paullina Simons", 4.0, "", ""),
            BookDetails(10L, "Soul music", "Terry Pratchett", 5.0, "", ""),
            BookDetails(11L, "Hogfather", "Terry Pratchett", 4.5, "", ""),
            BookDetails(12L, "SLammerkin", "Emma Donoghue", 4.5, "", ""),
            BookDetails(13L, "Room", "Emma Donoghue", 4.0, "", "")
    )

    val bookWithoutDescription = mockBooks.find { it.id == 10L }!!
    val bookWithDescription = mockBooks.find { it.id == 1L }!!

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
