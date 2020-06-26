package com.ciastek.library.user.books.di

import com.ciastek.library.user.books.view.UserBooksMVP
import com.ciastek.library.user.books.view.UserBooksPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class UserBooksModule {

    @Binds
    abstract fun bindUserBookSPresenter(presenter: UserBooksPresenter): UserBooksMVP.Presenter
}
