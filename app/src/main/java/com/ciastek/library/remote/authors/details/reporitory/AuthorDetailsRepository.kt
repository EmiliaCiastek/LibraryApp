package com.ciastek.library.remote.authors.details.reporitory

import io.reactivex.Single

interface AuthorDetailsRepository {

    fun getAuthor(authorId: Long): Single<AuthorDetails>
}