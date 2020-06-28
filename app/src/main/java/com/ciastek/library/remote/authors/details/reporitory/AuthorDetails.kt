package com.ciastek.library.remote.authors.details.reporitory

import com.ciastek.library.remote.books.list.repository.Book

data class AuthorDetails(val id: Long,
                         val name: String,
                         val lastName: String,
                         val birthDate: String,
                         val deathDate: String,
                         val website: String,
                         val genres: String,
                         val photoUrl: String,
                         val description: String,
                         val books: List<Book>) {

    fun isEmpty() = id == EMPTY_ID

    companion object {

        private const val EMPTY_ID = -1L

        fun empty() = AuthorDetails(EMPTY_ID, "", "", "", "", "", "", "", "", emptyList())
    }
}
