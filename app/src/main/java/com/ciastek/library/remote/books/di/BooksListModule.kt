package com.ciastek.library.remote.books.di

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.books.repository.BooksRepository
import com.ciastek.library.remote.books.repository.RemoteBooksRepository
import com.ciastek.library.remote.books.repository.RemoteBooksService
import com.ciastek.library.remote.books.view.BooksViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit

@Module
class BooksListModule {

    @Provides
    fun provideBooksViewModel(booksRepository: BooksRepository,
                              @UiScheduler uiScheduler: Scheduler) =
            BooksViewModelFactory(booksRepository, uiScheduler)
                    .create(BooksViewModel::class.java)

    @Provides
    fun provideBooksService(retrofit: Retrofit): RemoteBooksService =
            retrofit.create(RemoteBooksService::class.java)

    @Provides
    fun provideBooksRepository(booksService: RemoteBooksService,
                               @BackgroundScheduler backgroundScheduler: Scheduler): BooksRepository =
            RemoteBooksRepository(booksService, backgroundScheduler)
}
