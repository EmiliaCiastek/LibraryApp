package com.ciastek.library.user.books.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAllBooks(): Single<List<BookEntity>>

    @Query("SELECT count(id) FROM book WHERE id = :id")
    fun countBook(id: Long): Single<Int>

    @Insert
    fun insertBook(book: BookEntity): Completable

    @Delete
    fun deleteBook(book: BookEntity): Completable
}
