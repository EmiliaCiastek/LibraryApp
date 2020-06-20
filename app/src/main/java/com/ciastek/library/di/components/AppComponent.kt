package com.ciastek.library.di.components

import com.ciastek.library.di.modules.AppModule
import com.ciastek.library.di.modules.BookModule
import com.ciastek.library.di.modules.DatabaseModule
import com.ciastek.library.di.modules.NetworkModule
import com.ciastek.library.di.modules.RxModule
import com.ciastek.library.remote.authors.details.di.AuthorDetailsComponent
import com.ciastek.library.remote.authors.list.di.AuthorsListComponent
import com.ciastek.library.remote.books.details.di.BookDetailsComponent
import com.ciastek.library.remote.books.list.di.BooksListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, RxModule::class, NetworkModule::class])
interface AppComponent {
    fun createBooksComponent(): BooksComponent

    fun createEditBookComponent(bookModule: BookModule): EditBookComponent

    fun createNewBookComponent(): NewBookComponent

    fun createAuthorsListComponent(): AuthorsListComponent

    fun createBooksListComponent(): BooksListComponent

    fun createAuthorDetailsComponent(): AuthorDetailsComponent

    fun createBookDetailsComponent(): BookDetailsComponent
}