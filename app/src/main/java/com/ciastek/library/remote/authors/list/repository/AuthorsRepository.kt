package com.ciastek.library.remote.authors.list.repository

import io.reactivex.Single

interface AuthorsRepository {

    fun getAuthors(): Single<List<Author>>
}
