package com.ciastek.library.presenter

import com.ciastek.library.BooksContracts
import com.ciastek.library.model.db.BookDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class BooksPresenterTest {
    private lateinit var view: BooksContracts.View
    private lateinit var presenter: BooksPresenter
    private lateinit var bookDao: BookDao

    @Before
    fun setUp() {
        view = mock(BooksContracts.View::class.java)
        bookDao = mock(BookDao::class.java)
        presenter = BooksPresenter(bookDao)
        presenter.attachView(view)
    }

    @Test
    fun shouldStartNewBookActivity_whenNewBookButtonClicked() {
        presenter.newBookButtonClicked()

        verify(view).startNewBookActivity()
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }
}