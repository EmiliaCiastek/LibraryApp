package com.ciastek.library.di.modules

import com.ciastek.library.BooksContract
import com.ciastek.library.presenter.BooksPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class BooksModule {
    @Binds
    abstract fun bindPresenter(booksPresenter: BooksPresenter): BooksContract.Presenter
}