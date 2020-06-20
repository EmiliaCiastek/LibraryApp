package com.ciastek.library.remote.books.details.reporitory

data class BookDetails(val id: Long,
                       val title: String,
                       val author: String,
                       val rating: Double,
                       val coverUrl: String,
                       val description: String) {

    companion object {

        fun empty() = BookDetails(-1L, "", "", 0.0, "", "")
    }
}
