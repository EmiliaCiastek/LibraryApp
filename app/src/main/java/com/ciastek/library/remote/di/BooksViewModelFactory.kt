package com.ciastek.library.remote.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.repository.RemoteBooksRepository
import com.ciastek.library.remote.view.BooksViewModel
import io.reactivex.Scheduler

class BooksViewModelFactory(private val booksRepository: RemoteBooksRepository,
                            private val schedulerUi: Scheduler) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            BooksViewModel(booksRepository, schedulerUi) as T
}