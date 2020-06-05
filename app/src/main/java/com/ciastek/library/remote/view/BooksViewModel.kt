package com.ciastek.library.remote.view

import androidx.lifecycle.*
import com.ciastek.library.remote.model.Author
import com.ciastek.library.remote.model.Book
import com.ciastek.library.remote.repository.RemoteBooksRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BooksViewModel(private val booksRepository: RemoteBooksRepository): ViewModel() {

    private val mutableBooks: MutableLiveData<List<Pair<Book, Author>>> = MutableLiveData()
    val books: LiveData<List<BookModel>> = Transformations.map(mutableBooks) {
        it.map { (book, author) -> BookModel(book.title, "${author.lastName} ${author.name}") }
    }

    fun fetchBooks() {
        viewModelScope.launch {
            booksRepository.getBooks()
                    .toList()
                    .let {
                        mutableBooks.value = it
                    }

        }
    }
}