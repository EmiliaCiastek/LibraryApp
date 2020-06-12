package com.ciastek.library.remote.authors.details.di

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.authors.RemoteAuthorsService
import com.ciastek.library.remote.authors.details.reporitory.AuthorDetailsRepository
import com.ciastek.library.remote.authors.details.reporitory.RemoteAuthorDetailsRepository
import com.ciastek.library.remote.authors.details.view.AuthorDetailsViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class AuthorDetailsModule {

    @Provides
    fun provideAuthorDetailsViewModel(authorDetailsRepository: AuthorDetailsRepository,
                                      @UiScheduler uiScheduler: Scheduler) =
            AuthorDetailsViewModelFactory(authorDetailsRepository,
                                          uiScheduler).create(AuthorDetailsViewModel::class.java)

    @Provides
    fun provideAuthorDetailsRepository(authorsService: RemoteAuthorsService,
                                       @BackgroundScheduler backgroundScheduler: Scheduler): AuthorDetailsRepository =
            RemoteAuthorDetailsRepository(authorsService, backgroundScheduler)
}
