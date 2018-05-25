package com.ciastek.library.presenter

import com.ciastek.library.CreateBookContract
import com.ciastek.library.model.Book
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CreateBookPresenterTest {
    private lateinit var view: CreateBookContract.View
    private lateinit var presenter: CreateBookPresenter

    private val fakeBook = Book("title", "author")

    @Before
    fun setUp() {
        view = mock(CreateBookContract.View::class.java)
        presenter = CreateBookPresenter()
        presenter.attachView(view)
    }

    @Test
    fun shouldSetBookCreatedOnView_whenSaveBookButtonClicked() {
        presenter.saveBookButtonClicked(fakeBook)

        verify(view).setBookCreated(fakeBook)
    }
}