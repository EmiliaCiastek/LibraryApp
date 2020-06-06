package com.ciastek.library.remote.authors.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.remote.authors.view.RemoteAuthorsFragment
import dagger.Subcomponent

@Subcomponent(modules = [AuthorsListModule::class])
interface AuthorsListComponent {

    fun inject(authorsFragment: RemoteAuthorsFragment)

    companion object {

        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createAuthorsListComponent()

    }
}