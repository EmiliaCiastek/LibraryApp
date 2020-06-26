package com.ciastek.library.remote.books.details.reporitory

data class BookDetails(val id: Long,
                       val title: String,
                       val author: String,
                       val rating: Double,
                       val coverUrl: String,
                       val description: String) {

    fun isEmpty() = id == EMPTY_ID

    companion object {

        private const val EMPTY_ID = -1L

        fun empty() = BookDetails(EMPTY_ID, "", "", 0.0, "", "")
    }
}
