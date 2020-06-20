package com.ciastek.library.remote.books.details.view

data class BookDetailsView(val id: Long,
                           val title: String,
                           val author: String,
                           val rating: Double,
                           val coverUrl: String,
                           val description: String) {

    fun isEmpty() = id == -1L
}
