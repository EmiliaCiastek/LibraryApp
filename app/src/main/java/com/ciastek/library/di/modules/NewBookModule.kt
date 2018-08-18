package com.ciastek.library.di.modules

import com.ciastek.library.CreateBookContract
import com.ciastek.library.presenter.CreateBookPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class NewBookModule {
    @Binds
    abstract fun bindPresenter(createBookPresenter: CreateBookPresenter): CreateBookContract.Presenter
}