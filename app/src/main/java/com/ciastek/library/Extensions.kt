package com.ciastek.library

import com.ciastek.library.model.Book

fun Book.isValid() : Boolean = this.author.isNotBlank() && this.title.isNotBlank()