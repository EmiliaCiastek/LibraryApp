package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.BookWithAuthor
import io.reactivex.Single

interface RemoteBooksRepository {

    fun getBooks(): Single<List<BookWithAuthor>>
}
