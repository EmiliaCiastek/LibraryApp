package com.ciastek.library.remote.books.add

data class NewBook(val title: String,
                        val authorId: Long,
                        val rating: Double?,
                        val coverUrl: String?,
                        val description: String?)