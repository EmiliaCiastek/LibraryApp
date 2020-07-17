package com.ciastek.library.remote.authors.details.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ciastek.library.di.UiScheduler
import com.ciastek.library.remote.authors.details.reporitory.AuthorDetails
import com.ciastek.library.remote.authors.details.reporitory.AuthorDetailsRepository
import com.ciastek.library.common.books.BookModel
import com.ciastek.library.user.authors.repository.AuthorEntity
import com.ciastek.library.user.authors.repository.UserAuthorsRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

class AuthorDetailsViewModel(private val authorDetailsRepository: AuthorDetailsRepository,
                             private val userAuthorsRepository: UserAuthorsRepository,
                             @UiScheduler private val uiScheduler: Scheduler) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val mutableAuthorDetails: MutableLiveData<Pair<AuthorDetails, Boolean>> = MutableLiveData()
    val authorDetails: LiveData<AuthorDetailsView> = Transformations.map(mutableAuthorDetails) {
        AuthorDetailsView(it.first.id,
                          it.first.name,
                          it.first.lastName,
                          it.first.birthDate,
                          it.first.deathDate,
                          it.first.website,
                          it.first.genres,
                          it.first.photoUrl,
                          it.first.description,
                          it.first.books.map { book -> BookModel(book.id!!, book.title, "") },
                          it.second)
    }

    fun getAuthorDetails(authorId: Long) {
        authorDetailsRepository.getAuthor(authorId)
                .zipWith(userAuthorsRepository.isAuthorInFavourites(authorId),
                         BiFunction<AuthorDetails, Boolean, Pair<AuthorDetails, Boolean>>
                         { authorDetails, isInFavourites -> Pair(authorDetails, isInFavourites) }
                )
                .observeOn(uiScheduler)
                .subscribe({
                               mutableAuthorDetails.value = it
                           },
                           {
                               mutableAuthorDetails.value = Pair(AuthorDetails.empty(), false)
                           })
                .apply {
                    disposable.add(this)
                }
    }

    override fun onCleared() {
        disposable.clear()

        super.onCleared()
    }

    fun addAuthorToUserLibrary() {
        mutableAuthorDetails.value?.first?.let {
            if (it.isEmpty().not()) {
                userAuthorsRepository.isAuthorInFavourites(it.id)
                        .filter { isInUserLibrary -> isInUserLibrary.not() }
                        .flatMapCompletable { _ ->
                            userAuthorsRepository.addAuthor(it.toAuthorEntity())
                        }
                        .observeOn(uiScheduler)
                        .subscribe {}
                        .apply {
                            disposable.add(this)
                        }
            }
        }
    }

    fun removeAuthorFromUserLibrary() {
        mutableAuthorDetails.value?.let {
            if (it.first.isEmpty().not()) {
                userAuthorsRepository.removeAuthor(it.first.toAuthorEntity())
                        .observeOn(uiScheduler)
                        .subscribe {}
                        .apply {
                            disposable.add(this)
                        }
            }
        }
    }

    fun AuthorDetails.toAuthorEntity() =
            AuthorEntity(id, name, lastName)
}
