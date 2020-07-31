package com.ciastek.library.remote.books.add

import com.ciastek.library.common.StateRepository
import com.ciastek.library.common.StringProvider
import com.ciastek.library.remote.authors.list.repository.Author
import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import com.ciastek.library.remote.books.RemoteBooksService
import com.ciastek.library.remote.books.add.BookState.EditedState
import com.ciastek.library.remote.books.add.BookState.EmptyState
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
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
    private val booksService: RemoteBooksService = mock()
    private val bookStateRepository: StateRepository<BookState> = mock()
    private val errorMessage = "Error!!!!"
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
    private val expectedAuthorsNames = mutableListOf(mockString)
            .apply {
                addAll(fakeAuthors.map { "${it.lastName} ${it.name}" })
            }

    @BeforeEach
    fun setUp() {
        whenever(stringProvider.getString(any())).thenReturn(mockString)
        whenever(authorsRepository.getAuthors()).thenReturn(Single.just(fakeAuthors))
        whenever(view.authorPickedIntent()).thenReturn(Observable.empty())
        whenever(view.titleChangedIntent()).thenReturn(Observable.empty())
        whenever(view.descriptionChangedIntent()).thenReturn(Observable.empty())
        whenever(view.coverUrlChangedIntent()).thenReturn(Observable.empty())
        whenever(view.cancelFormIntent()).thenReturn(Observable.empty())
        whenever(view.saveFormIntent()).thenReturn(Observable.empty())
        whenever(bookStateRepository.isEmpty()).thenReturn(true)
        whenever(bookStateRepository.getLast()).thenReturn(EmptyState(expectedAuthorsNames))

        presenter = NewBookPresenter(authorsRepository, stringProvider, booksService, bookStateRepository, scheduler, scheduler)
    }

    @Test
    fun rendersLoadingStateWhineBindIntents() {
        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.LoadingState)
    }

    @Test
    fun rendersErrorStateWhenErrorDuringFetchingAuthors() {
        whenever(authorsRepository.getAuthors()).thenReturn(Single.error(Exception(errorMessage)))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.ErrorState(errorMessage))
    }

    @Test
    fun rendersEmptyStateWhenAuthorsFetched() {
        presenter.bindIntents(view)

        verify(view).renderBookState(EmptyState(expectedAuthorsNames))
    }

    @Test
    fun rendersEditedStateWhenTitleChangedWithNoPreviousState() {
        whenever(view.titleChangedIntent()).thenReturn(Observable.just(title))

        presenter.bindIntents(view)

        verify(view).renderBookState(EditedState(title = title,
                                                 description = "",
                                                 coverUrl = "",
                                                 authorPickedPosition = 0))
    }

    @Test
    fun rendersEditedStateWhenDescriptionChangedWithNoPreviousState() {
        whenever(view.descriptionChangedIntent()).thenReturn(Observable.just(description))

        presenter.bindIntents(view)

        verify(view).renderBookState(EditedState(title = "",
                                                 description = description,
                                                 coverUrl = "",
                                                 authorPickedPosition = 0))
    }

    @Test
    fun rendersEditedStateWhenCoverUrlChangedWithNoPreviousState() {
        whenever(view.coverUrlChangedIntent()).thenReturn(Observable.just(coverUrl))

        presenter.bindIntents(view)

        verify(view).renderBookState(EditedState(title = "",
                                                 description = "",
                                                 coverUrl = coverUrl,
                                                 authorPickedPosition = 0))
    }

    @Test
    fun rendersEditedStateWhenPickedAuthorChangedWithNoPreviousState() {
        whenever(view.authorPickedIntent()).thenReturn(Observable.just(authorPosition))

        presenter.bindIntents(view)

        verify(view).renderBookState(EditedState(title = "",
                                                 description = "",
                                                 coverUrl = "",
                                                 authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenTitleChangedWithPreviousState() {
        val newTitle = "new title"
        val titleSubject = PublishSubject.create<String>()
        whenever(view.titleChangedIntent()).thenReturn(titleSubject.hide())

        whenever(bookStateRepository.getLast()).thenReturn(EditedState(title, description, coverUrl, authorPosition))

        presenter.bindIntents(view)

        titleSubject.onNext(newTitle)

        verify(view).renderBookState(EditedState(title = newTitle,
                                                 description = description,
                                                 coverUrl = coverUrl,
                                                 authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenDescriptionChangedWithPreviousState() {
        val newDescription = "new description"
        val descriptionSubject = PublishSubject.create<String>()
        whenever(view.descriptionChangedIntent()).thenReturn(descriptionSubject.hide())

        whenever(bookStateRepository.getLast()).thenReturn(EditedState(title, description, coverUrl, authorPosition))

        presenter.bindIntents(view)

        descriptionSubject.onNext(newDescription)

        verify(view).renderBookState(EditedState(title = title,
                                                 description = newDescription,
                                                 coverUrl = coverUrl,
                                                 authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenCoverUrlChangedWithPreviousState() {
        val newCoverUrl = "new.cover.url.jpg"
        val coverUrlSubject = PublishSubject.create<String>()

        whenever(view.coverUrlChangedIntent()).thenReturn(coverUrlSubject.hide())
        whenever(bookStateRepository.getLast()).thenReturn(EditedState(title, description, coverUrl, authorPosition))

        presenter.bindIntents(view)

        coverUrlSubject.onNext(newCoverUrl)

        verify(view).renderBookState(EditedState(title = title,
                                                 description = description,
                                                 coverUrl = newCoverUrl,
                                                 authorPickedPosition = authorPosition))
    }

    @Test
    fun rendersEditedStateWhenAuthorChangedWithPreviousState() {
        val newAuthorPosition = 2
        val authorPositionSubject = PublishSubject.create<Int>()

        whenever(view.authorPickedIntent()).thenReturn(authorPositionSubject.hide())

        whenever(bookStateRepository.getLast()).thenReturn(EditedState(title, description, coverUrl, authorPosition))

        presenter.bindIntents(view)

        authorPositionSubject.onNext(newAuthorPosition)

        verify(view).renderBookState(EditedState(title = title,
                                                 description = description,
                                                 coverUrl = coverUrl,
                                                 authorPickedPosition = newAuthorPosition))
    }

    @Test
    fun rendersCanceledStateWhenCancelIntent() {
        whenever(view.cancelFormIntent()).thenReturn(Observable.just(Unit))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.CanceledState)
    }

    @Test
    fun savesDataWhenSaveIntent() {
        whenever(bookStateRepository.findLast(any())).thenReturn(EditedState(title, description, coverUrl, authorPosition))
        whenever(view.saveFormIntent()).thenReturn(Observable.just(Unit))
        whenever(booksService.addBook(any())).thenReturn(Completable.complete())

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.SavedState)
    }

    @Test
    fun renderErrorWhenSavingData() {
        whenever(bookStateRepository.findLast(any())).thenReturn(EditedState(title, description, coverUrl, authorPosition))
        whenever(view.saveFormIntent()).thenReturn(Observable.just(Unit))
        whenever(booksService.addBook(any())).thenReturn(Completable.error(Exception(errorMessage)))

        presenter.bindIntents(view)

        verify(view).renderBookState(BookState.ErrorState(errorMessage))
    }

    @Test
    fun clearsStateRepositoryWhenCancel() {
        whenever(view.cancelFormIntent()).thenReturn(Observable.just(Unit))

        presenter.bindIntents(view)

        verify(bookStateRepository).clear()
    }

    @Test
    fun clearsStateRepositoryWhenBookSaved() {
        whenever(bookStateRepository.findLast(any())).thenReturn(EditedState(title, description, coverUrl, authorPosition))
        whenever(view.saveFormIntent()).thenReturn(Observable.just(Unit))
        whenever(booksService.addBook(any())).thenReturn(Completable.complete())

        presenter.bindIntents(view)

        verify(bookStateRepository).clear()
    }

    @Test
    fun rendersLastEmptyStateWhenStateRepositoryNotEmpty() {
        whenever(bookStateRepository.isEmpty()).thenReturn(false)
        whenever(bookStateRepository.getLast()).thenReturn(EmptyState(expectedAuthorsNames))

        presenter.bindIntents(view)

        verify(view).renderBookState(EmptyState(expectedAuthorsNames))
    }

    @Test
    fun rendersLastEmptyAndEditedStateWhenStateRepositoryNotEmpty() {
        val expectedState = EditedState(title, description, coverUrl, authorPosition)
        whenever(bookStateRepository.isEmpty()).thenReturn(false)
        whenever(bookStateRepository.findLast(any())).thenReturn(EmptyState(expectedAuthorsNames))
        whenever(bookStateRepository.getLast()).thenReturn(expectedState)

        presenter.bindIntents(view)

        verify(view).renderBookState(EmptyState(expectedAuthorsNames))
        verify(view).renderBookState(expectedState)
    }
}
