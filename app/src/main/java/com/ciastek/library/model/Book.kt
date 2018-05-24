package com.ciastek.library.model

import android.os.Parcel
import android.os.Parcelable

data class Book(val title: String, val author: String) : Parcelable {
    var isRead: Boolean = false

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
        isRead = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeByte(if (isRead) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }


}