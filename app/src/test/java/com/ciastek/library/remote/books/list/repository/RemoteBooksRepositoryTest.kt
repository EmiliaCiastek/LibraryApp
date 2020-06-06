package com.ciastek.library.remote.books.list.repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RemoteBooksRepositoryTest {

    private val booksService: RemoteBooksService = mock()
    private lateinit var repository: RemoteBooksRepository
    private val scheduler = Schedulers.trampoline()
    private val books = listOf(
            Book("Wicked",
                 "Gregory Maguire",
                 1L),
            Book("Out of Oz",
                 "Gregory Maguire",
                 2L),
            Book("Looking for Alaska",
                 "John Green",
                 3L),
            Book("The fault in our stars",
                 "John Green",
                 4L),
            Book("All the bright places",
                 "Jennifer Niven",
                 5L)
    )

    @BeforeEach
    fun setUp() {
        repository = RemoteBooksRepository(booksService, scheduler)
        whenever(booksService.getBooks()).thenReturn(Single.just(books))
    }

    @Test
    fun shouldFetchBooksFromService() {
        val testObserver = repository.getBooks().test()

        verify(booksService).getBooks()
        testObserver.assertValue(books)
    }
}