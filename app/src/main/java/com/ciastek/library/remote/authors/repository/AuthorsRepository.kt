package com.ciastek.library.remote.authors.repository

import io.reactivex.Single

interface AuthorsRepository {

    fun getAuthors(): Single<List<Author>>
}
