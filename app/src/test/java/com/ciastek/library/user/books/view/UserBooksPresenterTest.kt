package com.ciastek.library.user.books.view

import com.ciastek.library.common.books.BookModel
import com.ciastek.library.user.books.repository.BookDao
import com.ciastek.library.user.books.repository.BookEntity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception

internal class UserBooksPresenterTest {

    private lateinit var presenter: UserBooksPresenter
    private val view: UserBooksMVP.View = mock()
    private val scheduler = Schedulers.trampoline()
    private val userBookDao: BookDao = mock()

    @BeforeEach
    fun setUp() {
        presenter = UserBooksPresenter(userBookDao, scheduler, scheduler)
    }

    @Test
    fun shouldShowErrorOnViewWhenAttach() {
        val message = "Very serious exception message!"
        whenever(userBookDao.getAllBooks()).thenReturn(Single.error(Exception(message)))

        presenter.attach(view)

        verify(view).showError(message)
    }

    @Test
    fun shouldShowBooksOnViewWhenAttach() {
        val mockBooks = listOf(BookEntity(0, "Gravity", "Tess Gerritsen"),
                               BookEntity(1, "Clean coder", "Robert C. Martin"))
        val expectedBooks = mockBooks.map { BookModel(it.id, it.title, it.author) }
        whenever(userBookDao.getAllBooks()).thenReturn(Single.just(mockBooks))

        presenter.attach(view)

        verify(view).showBooks(expectedBooks)
    }
}
