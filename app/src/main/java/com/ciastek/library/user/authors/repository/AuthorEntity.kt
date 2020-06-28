package com.ciastek.library.user.authors.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author")
data class AuthorEntity(@PrimaryKey val id: Long,
                        val name: String,
                        @ColumnInfo(name = "last_name") val lastName: String)