package com.ciastek.library.presenter

import com.ciastek.library.BooksContract
import com.ciastek.library.di.BackgroundScheduler
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.model.db.BookDao
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BooksPresenter @Inject constructor(private val booksDao: BookDao,
                                         @BackgroundScheduler private val subscriptionScheduler: Scheduler,
                                         @UiScheduler private val observationScheduler: Scheduler) : BooksContract.Presenter {
    private var view: BooksContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun attachView(view: BooksContract.View) {
        this.view = view
        booksDao.getBooks()
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler)
                .subscribe {
                    view.setBooks(it)
                }.let(compositeDisposable::add)
    }

    override fun detachView() {
        this.view = null
        compositeDisposable.dispose()
    }
}