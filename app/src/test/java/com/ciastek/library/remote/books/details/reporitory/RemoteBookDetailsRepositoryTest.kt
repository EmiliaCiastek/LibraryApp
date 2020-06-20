package com.ciastek.library.remote.books.details.reporitory

import com.ciastek.library.remote.books.RemoteBooksService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RemoteBookDetailsRepositoryTest {

    private val bookService: RemoteBooksService = mock()
    private val scheduler = Schedulers.trampoline()
    private val bookId = 1L
    private val bookDetails = BookDetails(bookId, "Looking for Alaska", "John Green", 5.0, "cover url", "description description description")
    lateinit var repository: RemoteBookDetailsRepository

    @BeforeEach
    fun setUp() {
        repository = RemoteBookDetailsRepository(bookService, scheduler)
        whenever(bookService.getBook(bookId)).thenReturn(Single.just(bookDetails))
    }

    @Test
    fun shouldFetchBookDetailsFromService() {
        val testObserver = repository.getBook(bookId).test()

        verify(bookService).getBook(bookId)
        testObserver.assertValue(bookDetails)
    }
}
