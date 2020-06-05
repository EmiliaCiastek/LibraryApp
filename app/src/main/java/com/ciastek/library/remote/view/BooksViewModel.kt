package com.ciastek.library.remote.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciastek.library.remote.model.BookWithAuthor
import com.ciastek.library.remote.repository.RemoteBooksRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class BooksViewModel(private val booksRepository: RemoteBooksRepository,
                     private val schedulerUi: Scheduler) : ViewModel() {

    private val mutableBooks: MutableLiveData<List<BookWithAuthor>> = MutableLiveData()
    val books: LiveData<List<BookModel>> = Transformations.map(mutableBooks) {
        it.map { book -> BookModel(book.title, book.author) }
    }
    private val disposable = CompositeDisposable()

    fun fetchBooks() {
        viewModelScope.launch {
            booksRepository.getBooks()
                    .observeOn(schedulerUi)
                    .subscribe({ books ->
                                   mutableBooks.value = books
                               },
                               {
                                   //TODO: Add exception loging
                                   mutableBooks.value = emptyList()
                               })
                    .apply {
                        disposable.add(this)
                    }
        }
    }

    override fun onCleared() {
        disposable.clear()

        super.onCleared()
    }
}