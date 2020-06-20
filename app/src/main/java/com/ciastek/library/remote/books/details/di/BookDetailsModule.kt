package com.ciastek.library.remote.books.details.di

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.books.RemoteBooksService
import com.ciastek.library.remote.books.details.reporitory.BookDetailsRepository
import com.ciastek.library.remote.books.details.reporitory.RemoteBookDetailsRepository
import com.ciastek.library.remote.books.details.view.BookDetailsViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class BookDetailsModule {

    @Provides
    fun provideBookDetailsViewModel(bookDetailsRepository: BookDetailsRepository,
                                    @UiScheduler uiScheduler: Scheduler) =
            BookDetailsViewModelFactory(bookDetailsRepository, uiScheduler)
                    .create(BookDetailsViewModel::class.java)

    @Provides
    fun provideBookDetailsRepository(remoteBooksService: RemoteBooksService,
                                     @BackgroundScheduler backgroundScheduler: Scheduler): BookDetailsRepository =
            RemoteBookDetailsRepository(remoteBooksService, backgroundScheduler)
}
