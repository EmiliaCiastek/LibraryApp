package com.ciastek.library.remote.books.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.books.repository.BooksRepository
import com.ciastek.library.remote.books.view.BooksViewModel
import io.reactivex.Scheduler

class BooksViewModelFactory(private val booksRepository: BooksRepository,
                            private val schedulerUi: Scheduler) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            BooksViewModel(booksRepository, schedulerUi) as T
}