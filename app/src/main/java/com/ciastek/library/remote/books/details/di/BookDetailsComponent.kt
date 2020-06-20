package com.ciastek.library.remote.books.details.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.remote.books.details.view.BookDetailsFragment
import dagger.Subcomponent

@Subcomponent(modules = [BookDetailsModule::class])
interface BookDetailsComponent {

    fun inject(fragment: BookDetailsFragment)

    companion object {

        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createBookDetailsComponent()
    }
}
