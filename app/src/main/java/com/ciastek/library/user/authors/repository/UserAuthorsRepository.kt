package com.ciastek.library.user.authors.repository

import io.reactivex.Completable
import io.reactivex.Single

interface UserAuthorsRepository {

    fun getAuthors(): Single<List<AuthorEntity>>

    fun addAuthor(author: AuthorEntity): Completable

    fun removeAuthor(author: AuthorEntity): Completable
}