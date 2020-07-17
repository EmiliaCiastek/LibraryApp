package com.ciastek.library.remote.books.details.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.books.details.reporitory.BookDetails
import com.ciastek.library.remote.books.details.reporitory.BookDetailsRepository
import com.ciastek.library.user.books.repository.BookEntity
import com.ciastek.library.user.books.repository.UserBookRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

class BookDetailsViewModel(private val bookDetailsRepository: BookDetailsRepository,
                           private val userBookRepository: UserBookRepository,
                           @UiScheduler private val uiScheduler: Scheduler) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val mutableBookDetails: MutableLiveData<Pair<BookDetails, Boolean>> = MutableLiveData()
    val bookDetails: LiveData<BookDetailsView> = Transformations.map(mutableBookDetails) {
        BookDetailsView(it.first.id,
                        it.first.title,
                        it.first.author,
                        it.first.rating,
                        it.first.coverUrl,
                        it.first.description,
                        it.second)
    }

    fun fetchBook(bookId: Long) {
        bookDetailsRepository.getBook(bookId)
                .zipWith(userBookRepository.isBookInFavourites(bookId),
                         BiFunction<BookDetails, Boolean, Pair<BookDetails, Boolean>>
                         { bookDetails, isInFavourites -> Pair(bookDetails, isInFavourites) }
                )
                .observeOn(uiScheduler)
                .subscribe({
                               mutableBookDetails.value = it
                           },
                           {
                               mutableBookDetails.value = Pair(BookDetails.empty(), false)
                           })
                .apply {
                    disposable.add(this)
                }
    }

    override fun onCleared() {
        disposable.clear()

        super.onCleared()
    }

    fun addBookToUserLibrary() {
        mutableBookDetails.value?.first?.let {
            if (it.isEmpty().not()) {
                userBookRepository.isBookInFavourites(it.id)
                        .filter { isInUserLibrary -> isInUserLibrary.not() }
                        .flatMapCompletable { _ -> userBookRepository.insertBook(it.toBookEntity()) }
                        .observeOn(uiScheduler)
                        .subscribe {}
                        .apply {
                            disposable.add(this)
                        }
            }
        }
    }

    private fun BookDetails.toBookEntity() =
            BookEntity(id, title, author)

    fun removeBookFromUserLibrary() {
        mutableBookDetails.value?.first?.let {
            if (it.isEmpty().not()) {
                userBookRepository.isBookInFavourites(it.id)
                        .observeOn(uiScheduler)
                        .filter { isInUserLibrary -> isInUserLibrary }
                        .flatMapCompletable { _ -> userBookRepository.removeBook(it.toBookEntity()) }
                        .subscribe {}
                        .apply {
                            disposable.add(this)
                        }
            }
        }
    }
}
