package com.ciastek.library.remote.authors.list.repository

import com.ciastek.library.di.BackgroundScheduler
import io.reactivex.Scheduler
import io.reactivex.Single

class RemoteAuthorsRepository (private val authorsService: RemoteAuthorsService,
                    @BackgroundScheduler private val backgroundScheduler: Scheduler) : AuthorsRepository {

    override fun getAuthors(): Single<List<Author>> =
            authorsService.getAuthors()
                    .subscribeOn(backgroundScheduler)
}
