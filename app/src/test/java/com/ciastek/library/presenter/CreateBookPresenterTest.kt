package com.ciastek.library.presenter

import com.ciastek.library.CreateBookContract
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.BookDao
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CreateBookPresenterTest {
    private lateinit var view: CreateBookContract.View
    private lateinit var presenter: CreateBookPresenter
    private lateinit var bookDao: BookDao

    private val fakeBook = Book(title = "title", author = "author", isbn = "123456")

    @Before
    fun setUp() {
        view = mock(CreateBookContract.View::class.java)
        bookDao = mock(BookDao::class.java)
        presenter = CreateBookPresenter(bookDao)
        presenter.attachView(view)
    }

    @Test
    fun shouldSetBookCreatedOnView_whenSaveBookButtonClickedAndBookIsValid() {
        presenter.saveBookButtonClicked(fakeBook)

        verify(view).setBookCreated(fakeBook)
    }

    @Test
    fun shouldDisplayError_whenSaveBookButtonClickedAndBookIsNotValid() {
        val notValidBook = Book(author = "", title = "", isbn = "")
        presenter.saveBookButtonClicked(notValidBook)

        verify(view).showError()
    }
}