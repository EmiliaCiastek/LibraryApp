package com.ciastek.library.user.authors.view

import com.ciastek.library.common.authors.AuthorModel
import com.ciastek.library.user.authors.repository.AuthorEntity
import com.ciastek.library.user.authors.repository.UserAuthorsRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserAuthorsPresenterTest {

    private lateinit var presenter: UserAuthorsPresenter
    private val repository: UserAuthorsRepository = mock()
    private val scheduler = Schedulers.trampoline()
    private val view: UserAuthorsMVP.View = mock()
    private val mockAuthors = listOf(AuthorEntity(0, "Robert C.", "Martin"),
                                     AuthorEntity(1, "Tess", "Gerritsen"))

    @BeforeEach
    fun setUp() {
        presenter = UserAuthorsPresenter(repository, scheduler)

        whenever(repository.getAuthors()).thenReturn(Single.just(mockAuthors))
    }

    @Test
    fun shouldFetchAndDisplayDataOnViewWhenAttach() {
        val expectedAuthors = mockAuthors.map { AuthorModel(it.id, it.name, it.lastName) }

        presenter.attach(view)

        verify(repository).getAuthors()
        verify(view).showAuthors(expectedAuthors)
    }
}