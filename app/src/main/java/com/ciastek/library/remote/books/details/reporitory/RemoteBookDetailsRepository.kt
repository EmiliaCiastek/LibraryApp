package com.ciastek.library.remote.books.details.reporitory

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.remote.books.RemoteBooksService
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

class RemoteBookDetailsRepository(private val booksService: RemoteBooksService,
                                  @BackgroundScheduler private val backgroundScheduler: Scheduler) : BookDetailsRepository {

    override fun getBook(bookId: Long): Single<BookDetails> =
            booksService.getBook(bookId)
                    .subscribeOn(backgroundScheduler)

    override fun removeBook(bookId: Long): Completable =
            booksService.deleteBook(bookId)
                    .subscribeOn(backgroundScheduler)
}
