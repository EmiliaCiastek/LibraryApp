package com.ciastek.library.model.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.ciastek.library.model.Book

@Dao
interface BookDao {
    @Insert(onConflict = REPLACE)
    fun addBook(book: Book)

    @Query("Select * from books")
    fun getBooks(): List<Book>

    @Update
    fun updateBook(book: Book)

    @Delete
    fun deleteBook(book: Book)
}