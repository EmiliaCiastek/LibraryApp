package com.ciastek.library.user.books.view

import com.ciastek.library.common.books.BookModel
import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.user.books.repository.BookDao
import com.ciastek.library.user.books.repository.BookEntity
import com.ciastek.library.user.books.view.UserBooksMVP.Presenter
import com.ciastek.library.user.books.view.UserBooksMVP.View
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserBooksPresenter @Inject constructor(private val bookDao: BookDao,
                                             @BackgroundScheduler private val backgroundScheduler: Scheduler,
                                             @UiScheduler private val uiScheduler: Scheduler) : Presenter {

    private val disposable = CompositeDisposable()
    private var view: View? = null

    override fun attach(view: View) {
        this.view = view

        bookDao.getAllBooks()
                .map { it.map { book -> book.mapToModel() } }
                .subscribeOn(backgroundScheduler)
                .observeOn(uiScheduler)
                .subscribe({ books ->
                               view.showBooks(books)
                           },
                           { exception ->
                               view.showError(exception.message)
                           }
                )
                .apply {
                    disposable.add(this)
                }
    }

    private fun BookEntity.mapToModel(): BookModel =
            BookModel(id, title, author)

    override fun detach() {
        disposable.clear()

        view = null
    }
}
