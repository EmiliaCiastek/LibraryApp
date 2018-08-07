package com.ciastek.library.model.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.ciastek.library.model.Book
import io.reactivex.Flowable

@Dao
interface BookDao {
    @Insert(onConflict = REPLACE)
    fun addBook(book: Book): Long

    @Query("Select * from books")
    fun getBooks(): Flowable<List<Book>>

    @Update
    fun updateBook(book: Book)

    @Delete
    fun deleteBook(book: Book)
}