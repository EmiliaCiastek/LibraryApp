package com.ciastek.library.di.components

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.di.modules.BooksModule
import com.ciastek.library.view.BooksListFragment
import dagger.Subcomponent

@Subcomponent(modules = [BooksModule::class])
interface BooksComponent {
    fun inject(booksFragment: BooksListFragment)

    companion object {
        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createBooksComponent()
    }
}