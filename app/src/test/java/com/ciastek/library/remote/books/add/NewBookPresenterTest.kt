package com.ciastek.library.remote.books.add

import com.ciastek.library.common.StringProvider
import com.ciastek.library.remote.authors.list.repository.Author
import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception

internal class NewBookPresenterTest {

    private lateinit var presenter: NewBookPresenter
    private val view: NewBookContract.View = mock()
    private val scheduler = Schedulers.trampoline()
    private val authorsRepository: AuthorsRepository = mock()
    private val stringProvider: StringProvider = mock()
    private val mockString = "mock string"
    private val title = "book title"
    private val description = "book description"
    private val coverUrl = "book.cover.url.jpg"
    private val authorPosition = 1
    private val fakeAuthors = listOf(Author("Tess", "Gerritsen", 1),
                                     Author("John", "Green", 2),
                                     Author("Jennifer", "Niven", 3),
                                     Author("George R.R.", "Martin", 4),
                                     Author("Robert C.", "Martin", 5))

    @BeforeEach
    fun setUp() {
        whenever(stringProvider.getString(any())).thenReturn(mockString)
        whenever(authorsRepository.getAuthors()).thenReturn(Single.just(fakeAuthors))
        whenever(view.authorPickedIntent()).thenReturn(Observable.empty())
        whenever(view.titleChangedIntent()).thenReturn(Observable.empty())
        whenever(view.descriptionChangedIntent()).thenReturn(Observable.empty())
        whenever(view.coverUrlChangedIntent()).thenReturn(Observable.empty())

        presenter = NewBookPresenter(authorsRepository, stringProvider, scheduler, scheduler)
    }

    @Test
    fun rendersLoadingStateWhineBindIntents() {
        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.LoadingState)
    }

    @Test
    fun rendersErrorStateWhenErrorDuringFetchingAuthors() {
        val errorMessage = "Error!!!!"
        whenever(authorsRepository.getAuthors()).thenReturn(Single.error(Exception(errorMessage)))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.ErrorState(errorMessage))
    }

    @Test
    fun rendersEmptyStateWhenAuthorsFetched() {
        val expectedAuthorsNames = mutableListOf(mockString)
                .apply {
                    addAll(fakeAuthors.map { "${it.lastName} ${it.name}" })
                }

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.EmptyState(expectedAuthorsNames))
    }

    @Test
    fun rendersEditedStateWhenTitleChangedWithNoPreviousState() {
        whenever(view.titleChangedIntent()).thenReturn(Observable.just(title))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.EditedState(title = title,
                                                           description = "",
                                                           coverUrl = "",
                                                           authorPickedPosition = 0))
    }

    @Test
    fun rendersEditedStateWhenDescriptionChangedWithNoPreviousState() {
        whenever(view.descriptionChangedIntent()).thenReturn(Observable.just(description))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.EditedState(title = "",
                                                           description = description,
                                                           coverUrl = "",
                                                           authorPickedPosition = 0))
    }

    @Test
    fun rendersEditedStateWhenCoverUrlChangedWithNoPreviousState() {
        whenever(view.coverUrlChangedIntent()).thenReturn(Observable.just(coverUrl))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.EditedState(title = "",
                                                           description = "",
                                                           coverUrl = coverUrl,
                                                           authorPickedPosition = 0))
    }

    @Test
    fun rendersEditedStateWhenPickedAuthorChangedWithNoPreviousState() {
        whenever(view.authorPickedIntent()).thenReturn(Observable.just(authorPosition))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.EditedState(title = "",
                                                           description = "",
                                                           coverUrl = "",
                                                           authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenTitleChangedWithPreviousState() {
        val newTitle = "new title"
        val titleSubject = PublishSubject.create<String>()
        whenever(view.titleChangedIntent()).thenReturn(titleSubject.hide())

        whenever(view.coverUrlChangedIntent()).thenReturn(Observable.just(coverUrl))
        whenever(view.descriptionChangedIntent()).thenReturn(Observable.just(description))
        whenever(view.authorPickedIntent()).thenReturn(Observable.just(authorPosition))

        presenter.bindIntents(view)
        titleSubject.onNext(title)

        titleSubject.onNext(newTitle)

        verify(view).renderBookState(BookState.EditedState(title = newTitle,
                                                           description = description,
                                                           coverUrl = coverUrl,
                                                           authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenDescriptionChangedWithPreviousState() {
        val newDescription = "new description"
        val descriptionSubject = PublishSubject.create<String>()
        whenever(view.descriptionChangedIntent()).thenReturn(descriptionSubject.hide())

        whenever(view.titleChangedIntent()).thenReturn(Observable.just(title))
        whenever(view.coverUrlChangedIntent()).thenReturn(Observable.just(coverUrl))
        whenever(view.authorPickedIntent()).thenReturn(Observable.just(authorPosition))

        presenter.bindIntents(view)
        descriptionSubject.onNext(description)

        descriptionSubject.onNext(newDescription)

        verify(view).renderBookState(BookState.EditedState(title = title,
                                                           description = newDescription,
                                                           coverUrl = coverUrl,
                                                           authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenCoverUrlChangedWithPreviousState() {
        val newCoverUrl = "new.cover.url.jpg"
        val coverUrlSubject = PublishSubject.create<String>()

        whenever(view.coverUrlChangedIntent()).thenReturn(coverUrlSubject.hide())

        whenever(view.descriptionChangedIntent()).thenReturn(Observable.just(description))
        whenever(view.titleChangedIntent()).thenReturn(Observable.just(title))
        whenever(view.authorPickedIntent()).thenReturn(Observable.just(authorPosition))

        presenter.bindIntents(view)
        coverUrlSubject.onNext(description)

        coverUrlSubject.onNext(newCoverUrl)

        verify(view).renderBookState(BookState.EditedState(title = title,
                                                           description = description,
                                                           coverUrl = newCoverUrl,
                                                           authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenAuthorChangedWithPreviousState() {
        val newAuthorPosition = 2
        val authorPositionSubject = PublishSubject.create<Int>()

        whenever(view.authorPickedIntent()).thenReturn(authorPositionSubject.hide())

        whenever(view.coverUrlChangedIntent()).thenReturn(Observable.just(coverUrl))
        whenever(view.descriptionChangedIntent()).thenReturn(Observable.just(description))
        whenever(view.titleChangedIntent()).thenReturn(Observable.just(title))

        presenter.bindIntents(view)
        authorPositionSubject.onNext(authorPosition)

        authorPositionSubject.onNext(newAuthorPosition)

        verify(view).renderBookState(BookState.EditedState(title = title,
                                                           description = description,
                                                           coverUrl = coverUrl,
                                                           authorPickedPosition = newAuthorPosition))
    }
}
