package com.ciastek.library.model.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.ciastek.library.model.Book
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DbTest {
    private lateinit var bookDao: BookDao
    private lateinit var libraryDatabase: LibraryDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        libraryDatabase = Room.inMemoryDatabaseBuilder(
                context, LibraryDatabase::class.java
        ).build()
        bookDao = libraryDatabase.bookDao()
    }

    @After
    fun tearDown() {
        libraryDatabase.close()
    }

    @Test
    fun shouldAddNewBookToDatabase() {
        val book = Book(title = "title", author = "author")
        bookDao.addBook(book)

        val actualBooks = bookDao.getBooks()

        assertEquals(1, actualBooks.size)
        val actualBook = actualBooks[0]
        assertEquals(book.title, actualBook.title)
        assertEquals(book.author, actualBook.author)
    }
}