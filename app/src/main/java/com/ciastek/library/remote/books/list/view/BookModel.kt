package com.ciastek.library.remote.books.list.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookModel(val id: Long, val title: String, val author: String): Parcelable