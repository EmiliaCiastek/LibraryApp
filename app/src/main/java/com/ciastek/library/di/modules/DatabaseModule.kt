package com.ciastek.library.di.modules

import android.content.Context
import com.ciastek.library.model.db.BookDao
import com.ciastek.library.model.db.LibraryDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DatabaseModule {
    @Provides
    @Singleton
    internal open fun provideDeviceDao(context: Context): BookDao =
            LibraryDatabase.getInstance(context).bookDao()
}