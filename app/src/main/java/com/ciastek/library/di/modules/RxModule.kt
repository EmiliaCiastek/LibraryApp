package com.ciastek.library.di.modules

import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class RxModule {

    @Provides
    @BackgroundScheduler
    fun provideBackgroundScheduler() = Schedulers.io()

    @Provides
    @UiScheduler
    fun provideUiScheduler() = AndroidSchedulers.mainThread()
}
