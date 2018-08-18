package com.ciastek.library.di.modules

import com.ciastek.library.EditBookContract
import com.ciastek.library.presenter.EditBookPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class EditBookModule {
    @Binds
    abstract fun bindPresenter(editBookPresenter: EditBookPresenter): EditBookContract.Presenter
}