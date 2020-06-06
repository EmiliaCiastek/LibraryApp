package com.ciastek.library.remote.authors.repository

import io.reactivex.Scheduler
import io.reactivex.Single

class RemoteAuthorsRepository(private val authorsService: RemoteAuthorsService,
                              private val backgroundScheduler: Scheduler) : AuthorsRepository {

    override fun getAuthors(): Single<List<Author>> =
            authorsService.getAuthors()
                    .subscribeOn(backgroundScheduler)
}
