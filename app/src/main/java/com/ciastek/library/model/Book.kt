package com.ciastek.library.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "books")
@Parcelize
data class Book(
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        val title: String,
        val author: String,
        var isRead: Boolean = false) : Parcelable