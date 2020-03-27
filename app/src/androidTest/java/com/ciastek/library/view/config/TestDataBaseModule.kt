package com.ciastek.library.view.config

import androidx.room.Room
import android.content.Context
import com.ciastek.library.di.modules.DatabaseModule
import com.ciastek.library.model.db.BookDao
import com.ciastek.library.model.db.LibraryDatabase
import dagger.Module

@Module
class TestDataBaseModule : DatabaseModule() {
    override fun provideDeviceDao(context: Context): BookDao =
            Room.inMemoryDatabaseBuilder(context.applicationContext, LibraryDatabase::class.java)
                    .build()
                    .bookDao()
}