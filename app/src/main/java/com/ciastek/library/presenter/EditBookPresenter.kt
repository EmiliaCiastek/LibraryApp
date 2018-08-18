package com.ciastek.library.presenter

import com.ciastek.library.EditBookContract
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.BookDao
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class EditBookPresenter @Inject constructor(private val book: Book,
                                            private val bookDao: BookDao,
                                            private val subscriptionScheduler: Scheduler,
                                            @UiScheduler private val observationScheduler: Scheduler) : EditBookContract.Presenter {

    private var view: EditBookContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    //TODO: add book validator
    override fun onSaveBookClicked(title: String, author: String, isbn: String, isRead: Boolean) {
        Completable.fromAction {
            bookDao.updateBook(Book(book.id, title, author, isbn, isRead))
        }
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler)
                .subscribe {
                    view?.bookSaved()
                }.let(compositeDisposable::add)
    }

    override fun onRemoveBookClicked() {
        //TODO: add dialog
        Completable.fromAction {
            bookDao.deleteBook(book)
        }
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler)
                .subscribe { view?.bookDeleted() }
                .let(compositeDisposable::add)
    }

    override fun attachView(view: EditBookContract.View) {
        this.view = view
        setBook()
    }

    override fun detachView() {
        view = null
        compositeDisposable.dispose()
    }

    private fun setBook() {
        view?.setAuthor(book.author)
        view?.setTitle(book.title)
        view?.setIsbn(book.isbn)
        view?.isRead(book.isRead)
    }
}