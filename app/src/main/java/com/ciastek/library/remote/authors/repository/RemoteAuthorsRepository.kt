package com.ciastek.library.remote.authors.repository

import io.reactivex.Single

interface RemoteAuthorsRepository {

    fun getAuthors(): Single<List<Author>>
}
