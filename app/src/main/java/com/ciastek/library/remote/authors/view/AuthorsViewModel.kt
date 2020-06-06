package com.ciastek.library.remote.authors.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ciastek.library.remote.authors.repository.Author
import com.ciastek.library.remote.authors.repository.RemoteAuthorsRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import kotlin.random.Random

class AuthorsViewModel(private val authorsRepository: RemoteAuthorsRepository,
                       private val schedulerUi: Scheduler) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val mutableAuthors: MutableLiveData<List<Author>> = MutableLiveData()
    val authors: LiveData<List<AuthorModel>> = Transformations.map(mutableAuthors) {
        it.map { author ->
            AuthorModel(name = author.name,
                        lastName = author.lastName,
                        numberOfBooks = getFakeNumberOfBooks())
        }
    }

    private fun getFakeNumberOfBooks() =
            Random.nextInt(1, 100)

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