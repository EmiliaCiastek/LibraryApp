package com.ciastek.library.remote.authors.details.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.remote.authors.details.view.AuthorDetailsFragment
import dagger.Subcomponent

@Subcomponent(modules = [AuthorDetailsModule::class])
interface AuthorDetailsComponent {

    fun inject(fragment: AuthorDetailsFragment)

    companion object {

        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createAuthorDetailsComponent()
    }
}
