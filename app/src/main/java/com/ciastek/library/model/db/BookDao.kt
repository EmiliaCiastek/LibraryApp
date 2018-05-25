package com.ciastek.library.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.ciastek.library.model.Book

@Dao
interface BookDao {
    @Insert(onConflict = REPLACE)
    fun addBook(book: Book)

    @Query("Select * from books")
    fun getBooks(): List<Book>
}