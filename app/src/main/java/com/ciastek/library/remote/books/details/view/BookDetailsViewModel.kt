package com.ciastek.library.remote.books.details.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.books.details.reporitory.BookDetails
import com.ciastek.library.remote.books.details.reporitory.BookDetailsRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class BookDetailsViewModel(private val bookDetailsRepository: BookDetailsRepository,
                           @UiScheduler private val uiScheduler: Scheduler) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val mutableBookDetails: MutableLiveData<BookDetails> = MutableLiveData()
    val bookDetails: LiveData<BookDetailsView> = Transformations.map(mutableBookDetails) {
        BookDetailsView(it.id,
                        it.title,
                        it.author,
                        it.rating,
                        it.coverUrl,
                        it.description)
    }

    fun fetchBook(bookId: Long) {
        bookDetailsRepository.getBook(bookId)
                .observeOn(uiScheduler)
                .subscribe({
                               mutableBookDetails.value = it
                           },
                           {
                               mutableBookDetails.value = BookDetails.empty()
                           })
                .apply {
                    disposable.add(this)
                }
    }

    override fun onCleared() {
        disposable.clear()

        super.onCleared()
    }
}
