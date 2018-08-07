package com.ciastek.library.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "books")
@Parcelize
data class Book(
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        val title: String,
        val author: String,
        val isbn: String,
        val isRead: Boolean = false) : Parcelable {

    override fun equals(other: Any?): Boolean = other is Book && other.id == id ?: false

    override fun hashCode(): Int = Objects.hashCode(id)
}