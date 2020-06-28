package com.ciastek.library.di.modules

import android.content.Context
import com.ciastek.library.LibraryDatabase
import com.ciastek.library.user.authors.repository.AuthorDao
import com.ciastek.library.user.books.repository.BookDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DatabaseModule {

    @Provides
    @Singleton
    internal open fun provideUserBooksDao(context: Context): BookDao =
            LibraryDatabase.getInstance(context).userBookDao()

    @Provides
    @Singleton
    internal open fun provideUserAuthorsDao(context: Context): AuthorDao =
            LibraryDatabase.getInstance(context).userAuthorDao()
}