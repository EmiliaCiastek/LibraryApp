package com.ciastek.library

interface EditBookContract {
    interface Presenter : Attachable<View> {
        fun onSaveBookClicked(title: String, author: String, isbn: String, isRead: Boolean)

        fun onRemoveBookClicked()
    }

    interface View {
        fun bookSaved()

        fun bookDeleted()

        fun setTitle(title: String)

        fun setAuthor(author: String)

        fun setIsbn(isbn: String)

        fun isRead(isRead: Boolean)
    }
}