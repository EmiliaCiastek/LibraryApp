package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.BookWithAuthor
import io.reactivex.Scheduler
import io.reactivex.Single

class RemoteBooksRepositoryImpl(private val booksService: RemoteBooksService,
                                private val backgroundScheduler: Scheduler) : RemoteBooksRepository {

    override fun getBooks(): Single<List<BookWithAuthor>> =
            booksService.getBooks()
                    .subscribeOn(backgroundScheduler)
}
