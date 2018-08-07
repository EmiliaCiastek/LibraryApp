package com.ciastek.library.presenter

import com.ciastek.library.EditBookContract
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.BookDao
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class EditBookPresenterTest {
    private lateinit var view: EditBookContract.View
    private lateinit var dao: BookDao
    private lateinit var presenter: EditBookPresenter
    private val fakeBook = Book(0, "title", "author", "123456")
    private val mockedScheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
        view = mock()
        dao = mock()

        presenter = EditBookPresenter(fakeBook, dao, mockedScheduler, mockedScheduler)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun shouldSetBookOnView_whenAttachViewAndViewNotNull() {
        presenter.attachView(view)

        verify(view).setAuthor(fakeBook.author)
        verify(view).setTitle(fakeBook.title)
        verify(view).setIsbn(fakeBook.isbn)
        verify(view).isRead(fakeBook.isRead)
    }

    @Test
    fun shouldUpdateBookInDao_whenOnSaveBookClicked() {
        presenter.attachView(view)
        presenter.onSaveBookClicked("new title", "new author", "789", true)

        verify(dao).updateBook(any())
    }

    @Test
    fun shouldInformViewAboutSavedBook_whenOnSaveBookClicked() {
        presenter.attachView(view)
        presenter.onSaveBookClicked("new title", "new author", "789", true)

        verify(view).bookSaved()
    }

    @Test
    fun shouldDeleteBookFromDao_whenOnRemoveBookClicked() {
        presenter.attachView(view)
        presenter.onRemoveBookClicked()

        verify(dao).deleteBook(fakeBook)
    }

    @Test
    fun shouldInformViewAboutSavedBook_whenOnRemoveBookClicked() {
        presenter.attachView(view)
        presenter.onRemoveBookClicked()

        verify(view).bookDeleted()
    }
}