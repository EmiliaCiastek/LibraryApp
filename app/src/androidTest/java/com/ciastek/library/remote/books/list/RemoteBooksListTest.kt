package com.ciastek.library.remote.books.list

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ciastek.library.MainActivity
import com.ciastek.library.R
import com.ciastek.library.common.BottomBarPage
import com.ciastek.library.common.DrawerPage
import com.ciastek.library.config.FakeRemoteBookService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteBooksListTest {

    private val drawerPage = DrawerPage()
    private val bottomBarPage = BottomBarPage()
    private val bottomBarButtons = listOf(R.id.nav_books, R.id.nav_authors)
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
    fun shouldDisplayRemoteLibraryBottomBar() {
        bottomBarButtons.forEach {
            bottomBarPage.verifyBottomBarButton(it)
        }
        bottomBarPage.verifyTabActive(R.id.nav_books)
    }

    @Test
    fun shouldDisplayBooksList() {
        fakeRemoteBookService.mockBooks.forEach {
            booksListPage.verifyBookVisibleOnList(it.title, it.author)
        }
    }

    @Test
    fun shouldOpenBookDetailsAfterClick() {
        val book = fakeRemoteBookService.bookWithoutDescription
        booksListPage.openBookDetails(book.title, book.author)
        booksListPage.verifyBookDetailsVisible()
    }
}
