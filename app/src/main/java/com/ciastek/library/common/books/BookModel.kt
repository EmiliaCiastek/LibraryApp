package com.ciastek.library.common.books

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookModel(val id: Long, val title: String, val author: String): Parcelable