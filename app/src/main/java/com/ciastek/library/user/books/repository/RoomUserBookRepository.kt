package com.ciastek.library.user.books.repository

import com.ciastek.library.di.BackgroundScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class RoomUserBookRepository
@Inject constructor(private val bookDao: BookDao,
                    @BackgroundScheduler private val backgroundScheduler: Scheduler) : UserBookRepository {

    override fun getAllBooks(): Single<List<BookEntity>> =
            bookDao.getAllBooks()
                    .subscribeOn(backgroundScheduler)

    override fun insertBook(book: BookEntity): Completable =
            bookDao.insertBook(book)
                    .subscribeOn(backgroundScheduler)

    override fun removeBook(book: BookEntity): Completable =
        bookDao.deleteBook(book)
                .subscribeOn(backgroundScheduler)

    override fun isBookInFavourites(bookId: Long): Single<Boolean> =
        bookDao.countBook(bookId)
                .map { it > 0 }
                .subscribeOn(backgroundScheduler)
}