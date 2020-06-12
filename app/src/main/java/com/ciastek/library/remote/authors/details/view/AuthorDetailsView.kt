package com.ciastek.library.remote.authors.details.view

import com.ciastek.library.remote.books.list.view.BookModel

data class AuthorDetailsView(val id: Long,
                             val name: String,
                             val lastName: String,
                             val birthDate: String,
                             val deathDate: String,
                             val website: String,
                             val genres: String,
                             val photoUrl: String,
                             val description: String,
                             val books: List<BookModel>) {

    fun isEmpty() = id == -1L
}
