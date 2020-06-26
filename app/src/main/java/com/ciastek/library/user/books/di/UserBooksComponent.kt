package com.ciastek.library.user.books.di

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.user.books.view.UserBooksFragment
import dagger.Subcomponent

@Subcomponent(modules = [UserBooksModule::class])
interface UserBooksComponent {

    fun inject(fragment: UserBooksFragment)

    companion object {

        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createUserBooksComponent()
    }
}
