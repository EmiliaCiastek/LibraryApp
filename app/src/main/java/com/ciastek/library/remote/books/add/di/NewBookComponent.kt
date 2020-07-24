package com.ciastek.library.remote.books.add.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.remote.books.add.NewBookFragment
import dagger.Subcomponent

@Subcomponent(modules = [NewBookModule::class])
interface NewBookComponent {

    fun inject(fragment: NewBookFragment)

    companion object {

        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createNewBookComponent()
    }
}
