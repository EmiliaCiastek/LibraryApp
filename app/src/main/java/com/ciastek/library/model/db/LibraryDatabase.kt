package com.ciastek.library.model.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.ciastek.library.model.Book

@Database(entities = [(Book::class)], version = 1)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

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