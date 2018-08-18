package com.ciastek.library.di.modules

import android.app.Application
import android.content.Context
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.model.db.BookDao
import com.ciastek.library.model.db.LibraryDatabase
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplicationContext() = application.applicationContext

    @Provides
    @Singleton
    fun provideDeviceDao(context: Context): BookDao =
            LibraryDatabase.getInstance(context).bookDao()

    @Provides
    fun provideSubscriptionScheduler() = Schedulers.io()

    @Provides
    @UiScheduler
    fun provideUiScheduler() = AndroidSchedulers.mainThread()
}