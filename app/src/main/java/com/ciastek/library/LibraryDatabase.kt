package com.ciastek.library

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.ciastek.library.user.books.repository.BookDao as BookEntityDao
import com.ciastek.library.user.books.repository.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun userBookDao(): BookEntityDao

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