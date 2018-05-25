package com.ciastek.library.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        @ColumnInfo val title: String,
        @ColumnInfo val author: String) {

    @ColumnInfo
    var isRead: Boolean = false
}