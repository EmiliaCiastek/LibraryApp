package com.ciastek.library.remote.authors.list.di

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import com.ciastek.library.remote.authors.list.repository.RemoteAuthorsRepository
import com.ciastek.library.remote.authors.list.repository.RemoteAuthorsService
import com.ciastek.library.remote.authors.list.view.AuthorsViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit

@Module
class AuthorsListModule {

    @Provides
    fun provideAuthorsViewModel(authorsRepository: AuthorsRepository,
                                @UiScheduler uiScheduler: Scheduler) =
            AuthorsViewModelFactory(authorsRepository,
                                    uiScheduler).create(AuthorsViewModel::class.java)

    @Provides
    fun provideAuthorsService(retrofit: Retrofit): RemoteAuthorsService =
            retrofit.create(RemoteAuthorsService::class.java)

    @Provides
    fun provideAuthorsRepository(authorsService: RemoteAuthorsService,
                                 @BackgroundScheduler backgroundScheduler: Scheduler): AuthorsRepository =
            RemoteAuthorsRepository(authorsService, backgroundScheduler)
}
