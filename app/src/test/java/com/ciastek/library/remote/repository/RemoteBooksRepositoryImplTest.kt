package com.ciastek.library.remote.repository

import com.nhaarman.mockito_kotlin.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RemoteBooksRepositoryImplTest {

    private val booksService: RemoteBooksService = mock()
    private lateinit var repository: RemoteBooksRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = RemoteBooksRepositoryImpl(booksService)
    }

    @Test
    fun shouldThrowNotImplementedError() {
        val testObserver = repository.getBooks2().test()

        testObserver.assertError(NotImplementedError::class.java)
    }
}