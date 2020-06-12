package com.ciastek.library.remote.authors.details.reporitory

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.remote.authors.RemoteAuthorsService
import io.reactivex.Scheduler
import io.reactivex.Single

class RemoteAuthorDetailsRepository(private val remoteAuthorsService: RemoteAuthorsService,
                                    @BackgroundScheduler private val backgroundScheduler: Scheduler) : AuthorDetailsRepository {

    override fun getAuthor(authorId: Long): Single<AuthorDetails> =
            remoteAuthorsService.getAuthor(authorId)
                    .subscribeOn(backgroundScheduler)
}
