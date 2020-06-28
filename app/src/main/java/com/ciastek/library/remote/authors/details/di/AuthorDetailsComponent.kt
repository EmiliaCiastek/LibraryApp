package com.ciastek.library.remote.authors.details.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.remote.authors.details.view.AuthorDetailsFragment
import com.ciastek.library.user.authors.di.UserAuthorsRepositoryModule
import dagger.Subcomponent

@Subcomponent(modules = [AuthorDetailsModule::class, UserAuthorsRepositoryModule::class])
interface AuthorDetailsComponent {

    fun inject(fragment: AuthorDetailsFragment)

    companion object {

        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createAuthorDetailsComponent()
    }
}
