package com.ciastek.library.remote.books.details.di

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.books.RemoteBooksService
import com.ciastek.library.remote.books.details.reporitory.BookDetailsRepository
import com.ciastek.library.remote.books.details.reporitory.RemoteBookDetailsRepository
import com.ciastek.library.remote.books.details.view.BookDetailsViewModel
import com.ciastek.library.user.books.repository.BookDao
import com.ciastek.library.user.books.repository.RoomUserBookRepository
import com.ciastek.library.user.books.repository.UserBookRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class BookDetailsModule {

    @Provides
    fun provideBookDetailsViewModel(bookDetailsRepository: BookDetailsRepository,
                                    userBookRepository: UserBookRepository,
                                    @UiScheduler uiScheduler: Scheduler) =
            BookDetailsViewModelFactory(bookDetailsRepository, userBookRepository, uiScheduler)
                    .create(BookDetailsViewModel::class.java)

    @Provides
    fun provideBookDetailsRepository(remoteBooksService: RemoteBooksService,
                                     @BackgroundScheduler backgroundScheduler: Scheduler): BookDetailsRepository =
            RemoteBookDetailsRepository(remoteBooksService, backgroundScheduler)

    @Provides
    fun provideUserBookRepository(bookDao: BookDao,
                                  @BackgroundScheduler backgroundScheduler: Scheduler):UserBookRepository =
            RoomUserBookRepository(bookDao, backgroundScheduler)
}
