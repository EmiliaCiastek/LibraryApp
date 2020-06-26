package com.ciastek.library.user.books.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAllBooks(): Single<List<BookEntity>>

    @Insert
    fun insertBook(book: BookEntity)

    @Delete
    fun deleteBook(book: BookEntity)
}
