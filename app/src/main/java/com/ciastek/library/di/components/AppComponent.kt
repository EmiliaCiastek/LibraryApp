package com.ciastek.library.di.components

import com.ciastek.library.di.modules.AppModule
import com.ciastek.library.di.modules.BookModule
import com.ciastek.library.di.modules.DatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {
    fun createBooksComponent(): BooksComponent

    fun createEditBookComponent(bookModule: BookModule): EditBookComponent

    fun createNewBookComponent(): NewBookComponent
}