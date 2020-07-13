package com.ciastek.library.remote.books.details

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ciastek.library.MainActivity
import com.ciastek.library.R
import com.ciastek.library.common.DrawerPage
import com.ciastek.library.config.FakeRemoteBookService
import com.ciastek.library.remote.books.list.RemoteBooksListPage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteBookDetailsTest {

    private val drawerPage = DrawerPage()
    private val bookDetailsPage = RemoteBookDetailsPage()
    private val booksListPage = RemoteBooksListPage()
    private val fakeRemoteBookService = FakeRemoteBookService()

    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        drawerPage.openDrawerItem(R.id.nav_remote_library)
    }

    @Test
    fun shouldDisplayBookDetailsWithDescription() {
        val book = fakeRemoteBookService.bookWithDescription
        booksListPage.openBookDetails(book.title, book.author)
        bookDetailsPage.verifyBookDetailsDisplayed(book)
        bookDetailsPage.verifyDescriptionVisible(book.description)
    }

    @Test
    fun shouldDisplayBookDetailsWithoutDescription() {
        val book = fakeRemoteBookService.bookWithoutDescription
        booksListPage.openBookDetails(book.title, book.author)
        bookDetailsPage.verifyBookDetailsDisplayed(book)
        bookDetailsPage.verifyEmptyDescriptionPlaceholder()
    }
}
