package com.ciastek.library.user.authors.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.user.authors.UserAuthorsFragment
import dagger.Subcomponent

@Subcomponent(modules = [UserAuthorsModule::class, UserAuthorsRepositoryModule::class])
interface UserAuthorsComponent {

    fun inject(fragment: UserAuthorsFragment)

    companion object {

        fun create(context: Context) =
                LibraryApp.get(context)
                        .getAppComponent()
                        .createUserAuthorsComponent()
    }
}