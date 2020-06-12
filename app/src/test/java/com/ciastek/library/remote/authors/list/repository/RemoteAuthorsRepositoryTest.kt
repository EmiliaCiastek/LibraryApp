package com.ciastek.library.remote.authors.list.repository

import com.ciastek.library.remote.authors.RemoteAuthorsService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RemoteAuthorsRepositoryTest {

    private lateinit var authorsRepository: RemoteAuthorsRepository

    private val authorsService: RemoteAuthorsService = mock()
    private val scheduler = Schedulers.trampoline()
    private val fakeAuthors = listOf(Author("Tess", "Gerritsen", 1, 10),
                                     Author("John", "Green", 2, 3),
                                     Author("Jennifer", "Niven", 3, 1),
                                     Author("George R.R.", "Martin", 4, 5),
                                     Author("Robert C.", "Martin", 5, 3))

    @BeforeEach
    fun setUp() {
        authorsRepository = RemoteAuthorsRepository(authorsService, scheduler)
        whenever(authorsService.getAuthors()).thenReturn(Single.just(fakeAuthors))
    }

    @Test
    fun shouldFetchAuthorsFromService() {
        val testObserver = authorsRepository.getAuthors().test()

        verify(authorsService).getAuthors()
        testObserver.assertValue(fakeAuthors)
    }
}