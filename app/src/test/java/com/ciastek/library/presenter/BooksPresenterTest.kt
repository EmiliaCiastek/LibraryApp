package com.ciastek.library.presenter

import com.ciastek.library.BooksContract
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.BookDao
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`


class BooksPresenterTest {
    private lateinit var view: BooksContract.View
    private lateinit var presenter: BooksPresenter
    private lateinit var bookDao: BookDao
    private val mockedScheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
        bookDao = mock()
        view = mock()

        presenter = BooksPresenter(bookDao, mockedScheduler, mockedScheduler)
    }

    @Test
    fun shouldSetBooksOnView_whenAttachView() {
        val books = listOf(
                Book(0, "Wicked", "Gregory Maguire", "9788392732235", true),
                Book(1, "Kotlin in action", "Dimitry Jemerov", "9781617293290", false)
        )
        `when`(bookDao.getBooks()).thenReturn(Flowable.fromArray(books))
        presenter.attachView(view)

        verify(view).setBooks(books)
    }
}