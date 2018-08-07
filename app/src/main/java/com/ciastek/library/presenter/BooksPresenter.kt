package com.ciastek.library.presenter

import com.ciastek.library.BooksContract
import com.ciastek.library.model.db.BookDao
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class BooksPresenter(private val booksDao: BookDao,
                     private val subscriptionScheduler: Scheduler,
                     private val observationScheduler: Scheduler) : BooksContract.Presenter {
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