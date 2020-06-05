package com.ciastek.library.remote.repository

import com.ciastek.library.remote.model.BookWithAuthor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RemoteBooksRepositoryImplTest {

    private val booksService: RemoteBooksService = mock()
    private lateinit var repository: RemoteBooksRepositoryImpl
    private val scheduler = Schedulers.trampoline()
    private val books = listOf(
            BookWithAuthor("Wicked", "Gregory Maguire", 1L),
            BookWithAuthor("Out of Oz", "Gregory Maguire", 2L),
            BookWithAuthor("Looking for Alaska", "John Green", 3L),
            BookWithAuthor("The fault in our stars", "John Green", 4L),
            BookWithAuthor("All the bright places", "Jennifer Niven", 5L)
    )

    @BeforeEach
    fun setUp() {
        repository = RemoteBooksRepositoryImpl(booksService, scheduler)
        whenever(booksService.getBooks()).thenReturn(Single.just(books))
    }

    @Test
    fun shouldFetchBooksFromService() {
        val testObserver = repository.getBooks().test()

        verify(booksService).getBooks()
        testObserver.assertValue(books)
    }
}