package com.ciastek.library.remote.books.list.repository

data class Book(val title: String, val author: String, var id: Long?) {

    companion object {

        fun empty() = Book("", "", -1L)
    }
}