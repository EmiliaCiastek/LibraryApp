package com.ciastek.library.remote.books.add

import com.ciastek.library.R
import com.ciastek.library.common.StringProvider
import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import com.ciastek.library.remote.books.add.BookState.EmptyState
import com.ciastek.library.remote.books.add.BookState.ErrorState
import com.ciastek.library.remote.books.add.NewBookContract.Presenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewBookPresenter @Inject constructor(private val authorsRepository: AuthorsRepository,
                                           private val stringProvider: StringProvider,
                                           @BackgroundScheduler private val backgroundScheduler: Scheduler,
                                           @UiScheduler private val uiScheduler: Scheduler) : Presenter {

    private val disposable = CompositeDisposable()

    private var authors = mapOf<String, Long>()
    private val authorNames = mutableListOf(stringProvider.getString(R.string.no_author))

    private val states = mutableListOf<BookState>()

    override fun bindIntents(view: NewBookContract.View) {
        BookState.LoadingState.let {
            states.add(it)
            view.renderBookState(it)
        }

        fetchAuthors(view)

        subscribeToAuthorPickedIntent(view)

        view.coverUrlChangedIntent()
                .observeOn(uiScheduler)
                .subscribe { view.renderBookState(reduce(states.last(), it)) }
                .apply {
                    disposable.add(this)
                }
    }

    private fun subscribeToAuthorPickedIntent(view: NewBookContract.View) {
        view.authorPickedIntent()
                .observeOn(uiScheduler)
                .subscribe { view.renderBookState(reduce(states.last(), it)) }
                .apply {
                    disposable.add(this)
                }
    }

    private fun reduce(previousState: BookState, pickedAuthorPosition: Int): BookState =
            if (previousState is BookState.EditedState) {
                BookState.EditedState(previousState.title,
                                      previousState.description,
                                      previousState.coverUrl,
                                      pickedAuthorPosition).apply {
                    states.add(this)
                }
            } else {
                BookState.EditedState("", "", "", pickedAuthorPosition).apply {
                    states.add(this)
                }
            }

    private fun reduce(previousState: BookState, coverUrl: String): BookState =
            if (previousState is BookState.EditedState) {
                BookState.EditedState(previousState.title,
                                      previousState.description,
                                      coverUrl,
                                      previousState.authorPickedPosition).apply {
                    states.add(this)
                }
            } else {
                BookState.EditedState("", "", coverUrl, 0).apply {
                    states.add(this)
                }
            }

    private fun fetchAuthors(view: NewBookContract.View) {
        authorsRepository.getAuthors()
                .map {
                    it.map { author ->
                        Pair(stringProvider.getString(R.string.author_data,
                                                      author.lastName,
                                                      author.name), author.id)
                    }
                }
                .map { it.toMap() }
                .subscribeOn(backgroundScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                               authors = it
                               authorNames.addAll(authors.keys.toList())
                               val state = EmptyState(authorNames)
                               states.add(state)
                               view.renderBookState(state)
                           },
                           { exception ->
                               val state = ErrorState(exception.message.toString())
                               states.add(state)
                               view.renderBookState(state)
                           })
                .apply {
                    apply { disposable.add(this) }
                }
    }

    override fun unbindIntents() {
        disposable.clear()
    }
}