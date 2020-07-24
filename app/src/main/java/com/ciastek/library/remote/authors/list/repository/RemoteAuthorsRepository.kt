package com.ciastek.library.remote.authors.list.repository

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.remote.authors.RemoteAuthorsService
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class RemoteAuthorsRepository @Inject constructor (private val authorsService: RemoteAuthorsService,
                                                   @BackgroundScheduler private val backgroundScheduler: Scheduler) : AuthorsRepository {

    override fun getAuthors(): Single<List<Author>> =
            authorsService.getAuthors()
                    .subscribeOn(backgroundScheduler)
}
