package com.ciastek.library.di.components

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.di.modules.NewBookModule
import com.ciastek.library.view.CreateBookActivity
import dagger.Subcomponent

@Subcomponent(modules = [NewBookModule::class])
interface NewBookComponent {
    fun inject(createBookActivity: CreateBookActivity)

    companion object {
        fun create(context: Context) = LibraryApp.get(context)
                .getAppComponent()
                .createNewBookComponent()
    }
}