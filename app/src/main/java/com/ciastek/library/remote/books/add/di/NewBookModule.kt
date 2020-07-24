package com.ciastek.library.remote.books.add.di

import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import com.ciastek.library.remote.authors.list.repository.RemoteAuthorsRepository
import com.ciastek.library.remote.books.add.NewBookContract
import com.ciastek.library.remote.books.add.NewBookPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class NewBookModule {

    @Binds
    abstract fun bindPresenter(presenter: NewBookPresenter): NewBookContract.Presenter

    @Binds
    abstract fun bindAuthorsRepository(repository: RemoteAuthorsRepository): AuthorsRepository
}
