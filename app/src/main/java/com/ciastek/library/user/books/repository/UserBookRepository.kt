package com.ciastek.library.user.books.repository

import io.reactivex.Completable
import io.reactivex.Single

interface UserBookRepository {

    fun getAllBooks(): Single<List<BookEntity>>

    fun insertBook(book: BookEntity): Completable

    fun removeBook(book: BookEntity): Completable

    fun isBookInFavourites(bookId: Long): Single<Boolean>
}
