package com.ciastek.library.view.config

import androidx.room.Room
import android.content.Context
import com.ciastek.library.di.modules.DatabaseModule
import com.ciastek.library.LibraryDatabase
import com.ciastek.library.user.books.repository.BookDao
import dagger.Module

@Module
class TestDataBaseModule : DatabaseModule() {

    override fun provideUserBooksDao(context: Context): BookDao =
            Room.inMemoryDatabaseBuilder(context.applicationContext, LibraryDatabase::class.java)
                    .build()
                    .userBookDao()
}