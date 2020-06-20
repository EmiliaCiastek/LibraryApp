package com.ciastek.library.remote.books.list.repository

import com.ciastek.library.remote.books.RemoteBooksService
import io.reactivex.Scheduler
import io.reactivex.Single

class RemoteBooksRepository(private val booksService: RemoteBooksService,
                            private val backgroundScheduler: Scheduler) : BooksRepository {

    override fun getBooks(): Single<List<Book>> =
            booksService.getBooks()
                    .subscribeOn(backgroundScheduler)
}
