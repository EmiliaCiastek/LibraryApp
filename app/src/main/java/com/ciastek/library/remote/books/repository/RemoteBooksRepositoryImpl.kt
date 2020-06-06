package com.ciastek.library.remote.books.repository

import io.reactivex.Scheduler
import io.reactivex.Single

class RemoteBooksRepositoryImpl(private val booksService: RemoteBooksService,
                                private val backgroundScheduler: Scheduler) : RemoteBooksRepository {

    override fun getBooks(): Single<List<Book>> =
            booksService.getBooks()
                    .subscribeOn(backgroundScheduler)
}
