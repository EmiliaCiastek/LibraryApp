package com.ciastek.library.di.modules

import android.app.Application
import android.content.Context
import com.ciastek.library.common.StringProvider
import com.ciastek.library.common.StringProviderImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    fun provideStringProvider(context: Context): StringProvider = StringProviderImpl(context)
}
