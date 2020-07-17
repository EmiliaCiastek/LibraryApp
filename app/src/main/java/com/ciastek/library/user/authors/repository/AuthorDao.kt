package com.ciastek.library.user.authors.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface AuthorDao {

    @Query("SELECT * FROM author")
    fun getAllAuthors(): Single<List<AuthorEntity>>

    @Query("SELECT count(id) FROM author WHERE id = :id")
    fun countAuthor(id: Long): Single<Int>

    @Insert
    fun insertAuthor(author: AuthorEntity): Completable

    @Delete
    fun deleteAuthor(author: AuthorEntity): Completable
}
