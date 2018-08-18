package com.ciastek.library.di.modules

import com.ciastek.library.model.Book
import dagger.Module
import dagger.Provides

@Module
class BookModule(private val book: Book) {
    @Provides
    fun provideBook() = book
}