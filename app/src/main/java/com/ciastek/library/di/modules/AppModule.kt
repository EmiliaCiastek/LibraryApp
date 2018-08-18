package com.ciastek.library.di.modules

import android.app.Application
import com.ciastek.library.di.UiScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplicationContext() = application.applicationContext

    @Provides
    fun provideSubscriptionScheduler() = Schedulers.io()

    @Provides
    @UiScheduler
    fun provideUiScheduler() = AndroidSchedulers.mainThread()
}