package com.ciastek.library

import com.ciastek.library.model.Book

interface EditBookContract {
    interface Presenter : Attachable<View> {
        fun onSaveBookClicked(title: String, author: String, isbn: String, isRead: Boolean)

        fun onRemoveBookClicked()
    }

    interface View {
        fun bookSaved(book: Book)

        fun bookDeleted(book: Book)

        fun setTitle(title: String)

        fun setAuthor(author: String)

        fun setIsbn(isbn: String)

        fun isRead(isRead: Boolean)
    }
}