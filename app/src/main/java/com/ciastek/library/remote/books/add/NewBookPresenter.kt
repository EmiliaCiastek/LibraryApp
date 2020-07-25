package com.ciastek.library.remote.books.add

import com.ciastek.library.R
import com.ciastek.library.common.StringProvider
import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import com.ciastek.library.remote.books.add.BookState.EmptyState
import com.ciastek.library.remote.books.add.BookState.ErrorState
import com.ciastek.library.remote.books.add.NewBookContract.Presenter
import com.ciastek.library.remote.books.add.NewBookContract.View
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

    override fun bindIntents(view: View) {
        BookState.LoadingState.let {
            states.add(it)
            view.renderBookState(it)
        }

        fetchAuthors(view)

        subscribeToTitleChangedIntent(view)
        subscribeToDescriptionChangedIntent(view)
        subscribeToAuthorPickedIntent(view)
        subscribeToCoverUrlChangedIntent(view)
    }

    private fun subscribeToTitleChangedIntent(view: View) {
        view.titleChangedIntent()
                .observeOn(uiScheduler)
                .subscribe {
                    view.renderBookState(reduce(states.last(), title = it))
                }
                .apply {
                    disposable.add(this)
                }
    }

    private fun subscribeToDescriptionChangedIntent(view: View) {
        view.descriptionChangedIntent()
                .observeOn(uiScheduler)
                .subscribe {
                    view.renderBookState(reduce(states.last(), description = it))
                }
                .apply {
                    disposable.add(this)
                }
    }

    private fun subscribeToCoverUrlChangedIntent(view: View) {
        view.coverUrlChangedIntent()
                .observeOn(uiScheduler)
                .subscribe {
                    view.renderBookState(reduce(states.last(), coverUrl = it))
                }
                .apply {
                    disposable.add(this)
                }
    }

    private fun subscribeToAuthorPickedIntent(view: View) {
        view.authorPickedIntent()
                .observeOn(uiScheduler)
                .subscribe { view.renderBookState(reduce(states.last(), pickedAuthorPosition = it)) }
                .apply {
                    disposable.add(this)
                }
    }

    private fun reduce(previousState: BookState,
                       title: String? = null,
                       description: String? = null,
                       coverUrl: String? = null,
                       pickedAuthorPosition: Int? = null): BookState =
            if (previousState is BookState.EditedState) {
                BookState.EditedState(title ?: previousState.title,
                                      description ?: previousState.description,
                                      coverUrl ?: previousState.coverUrl,
                                      pickedAuthorPosition
                                              ?: previousState.authorPickedPosition).apply {
                    states.add(this)
                }
            } else {
                BookState.EditedState(title ?: "",
                                      description ?: "",
                                      coverUrl ?: "",
                                      pickedAuthorPosition ?: 0).apply {
                    states.add(this)
                }
            }

    private fun fetchAuthors(view: View) {
        authorsRepository.getAuthors()
                .map {
                    it.map { author ->
                        Pair("${author.lastName} ${author.name}", author.id)
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
