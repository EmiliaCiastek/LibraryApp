package com.ciastek.library.presenter

import com.ciastek.library.CreateBookContract
import com.ciastek.library.isValid
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.BookDao
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class CreateBookPresenter(private val booksDao: BookDao,
                          private val subscriptionScheduler: Scheduler,
                          private val observationScheduler: Scheduler) : CreateBookContract.Presenter {
    private var view: CreateBookContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun saveBookButtonClicked(book: Book) {
        if (book.isValid()) {
            Completable.fromAction { booksDao.addBook(book) }
                    .subscribeOn(subscriptionScheduler)
                    .observeOn(observationScheduler)
                    .subscribe { view?.setBookCreated() }
                    .let(compositeDisposable::add)
        } else
            view?.showError()
    }

    override fun attachView(view: CreateBookContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
        compositeDisposable.dispose()
    }
}