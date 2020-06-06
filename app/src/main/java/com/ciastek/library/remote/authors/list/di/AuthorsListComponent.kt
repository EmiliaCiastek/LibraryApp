package com.ciastek.library.remote.authors.list.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.remote.authors.list.view.RemoteAuthorsFragment
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