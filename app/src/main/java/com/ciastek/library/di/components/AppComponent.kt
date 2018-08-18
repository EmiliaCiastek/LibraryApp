package com.ciastek.library.di.components

import com.ciastek.library.di.modules.AppModule
import com.ciastek.library.di.modules.BookModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun createBooksComponent(): BooksComponent

    fun createEditBookComponent(bookModule: BookModule): EditBookComponent

    fun createNewBookComponent(): NewBookComponent
}