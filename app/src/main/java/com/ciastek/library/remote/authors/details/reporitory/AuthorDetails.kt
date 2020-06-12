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

    companion object {

        fun empty() = AuthorDetails(-1, "", "", "", "", "", "", "", "", emptyList())
    }
}
