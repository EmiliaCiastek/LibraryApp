package com.ciastek.library.remote.books.details.reporitory

import io.reactivex.Single

interface BookDetailsRepository {

    fun getBook(bookId: Long): Single<BookDetails>
}
