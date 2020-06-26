package com.ciastek.library.user.books.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(@PrimaryKey val id: Long,
                      @ColumnInfo val title: String,
                      @ColumnInfo val author: String)
