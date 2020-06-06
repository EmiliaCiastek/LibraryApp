package com.ciastek.library.remote.authors.list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ciastek.library.remote.authors.list.repository.Author
import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class AuthorsViewModel(private val authorsRepository: AuthorsRepository,
                       private val schedulerUi: Scheduler) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val mutableAuthors: MutableLiveData<List<Author>> = MutableLiveData()
    val authors: LiveData<List<AuthorModel>> = Transformations.map(mutableAuthors) {
        it.map { author ->
            AuthorModel(id = author.id,
                        name = author.name,
                        lastName = author.lastName,
                        numberOfBooks = author.numberOfBooks)
        }
    }

    fun fetchAuthors() {
        authorsRepository.getAuthors()
                .observeOn(schedulerUi)
                .subscribe({
                               mutableAuthors.value = it
                           },
                           {
                               mutableAuthors.value = emptyList()
                               //TODO: Add error logging
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