package com.ciastek.library

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.ciastek.library.user.authors.repository.AuthorDao
import com.ciastek.library.user.authors.repository.AuthorEntity
import com.ciastek.library.user.books.repository.BookDao
import com.ciastek.library.user.books.repository.BookEntity

@Database(entities = [BookEntity::class, AuthorEntity::class], version = 1)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun userBookDao(): BookDao

    abstract fun userAuthorDao(): AuthorDao

    companion object {
        private var instance: LibraryDatabase? = null

        fun getInstance(context: Context): LibraryDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                                                LibraryDatabase::class.java, "library.db")
                        .build()
            }

            return instance!!
        }
    }
}