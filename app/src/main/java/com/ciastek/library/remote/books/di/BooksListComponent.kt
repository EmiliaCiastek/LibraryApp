package com.ciastek.library.remote.books.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.remote.books.view.RemoteBooksFragment
import dagger.Subcomponent

@Subcomponent(modules = [BooksListModule::class])
interface BooksListComponent {

    fun inject(booksFragment: RemoteBooksFragment)

    companion object {

        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createBooksListComponent()
    }
}
