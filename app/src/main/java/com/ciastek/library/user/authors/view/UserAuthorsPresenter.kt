package com.ciastek.library.user.authors.view

import com.ciastek.library.common.authors.AuthorModel
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.user.authors.repository.AuthorEntity
import com.ciastek.library.user.authors.repository.UserAuthorsRepository
import com.ciastek.library.user.authors.view.UserAuthorsMVP.Presenter
import com.ciastek.library.user.authors.view.UserAuthorsMVP.View
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserAuthorsPresenter @Inject constructor(private val repository: UserAuthorsRepository,
                                               @UiScheduler private val uiScheduler: Scheduler) : Presenter {

    private var view: View? = null
    private val disposable = CompositeDisposable()

    override fun attach(view: View) {
        this.view = view
        repository.getAuthors()
                .observeOn(uiScheduler)
                .map { authors -> authors.map { it.toAuthorModel() } }
                .subscribe { authors ->
                    view.showAuthors(authors)
                }
                .apply {
                    disposable.add(this)
                }
    }

    override fun detach() {
        disposable.clear()

        view = null
    }

    private fun AuthorEntity.toAuthorModel(): AuthorModel =
            AuthorModel(id, name, lastName)
}


