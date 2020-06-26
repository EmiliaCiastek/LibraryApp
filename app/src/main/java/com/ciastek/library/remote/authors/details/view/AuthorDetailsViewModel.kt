package com.ciastek.library.remote.authors.details.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.authors.details.reporitory.AuthorDetails
import com.ciastek.library.remote.authors.details.reporitory.AuthorDetailsRepository
import com.ciastek.library.common.books.BookModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class AuthorDetailsViewModel(private val authorDetailsRepository: AuthorDetailsRepository,
                             @UiScheduler private val uiScheduler: Scheduler) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val mutableAuthorDetails: MutableLiveData<AuthorDetails> = MutableLiveData()
    val authorDetails: LiveData<AuthorDetailsView> = Transformations.map(mutableAuthorDetails) {
        AuthorDetailsView(it.id,
                          it.name,
                          it.lastName,
                          it.birthDate,
                          it.deathDate,
                          it.website,
                          it.genres,
                          it.photoUrl,
                          it.description,
                          it.books.map { book ->
                              BookModel(book.id!!,
                                                                                            book.title,
                                                                                            "")
                          }
        )
    }

    fun getAuthorDetails(authorId: Long) {
        authorDetailsRepository.getAuthor(authorId)
                .observeOn(uiScheduler)
                .subscribe({
                               mutableAuthorDetails.value = it
                           },
                           {
                               mutableAuthorDetails.value = AuthorDetails.empty()
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
