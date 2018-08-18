package com.ciastek.library.di.components

import android.content.Context
import com.ciastek.library.LibraryApp
import com.ciastek.library.di.modules.BookModule
import com.ciastek.library.di.modules.EditBookModule
import com.ciastek.library.model.Book
import com.ciastek.library.view.EditBookFragment
import dagger.Subcomponent

@Subcomponent(modules = [EditBookModule::class, BookModule::class])
interface EditBookComponent {
    fun inject(editBookFragment: EditBookFragment)

    companion object {
        fun create(context: Context, book: Book) = LibraryApp.get(context)
                .getAppComponent()
                .createEditBookComponent(BookModule(book))
    }
}