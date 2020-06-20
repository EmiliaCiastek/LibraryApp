package com.ciastek.library.remote.books.details.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.books.details.reporitory.BookDetailsRepository
import com.ciastek.library.remote.books.details.view.BookDetailsViewModel
import io.reactivex.Scheduler

class BookDetailsViewModelFactory(private val bookDetailsRepository: BookDetailsRepository,
                                  private val uiScheduler: Scheduler): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
       BookDetailsViewModel(bookDetailsRepository, uiScheduler) as T
}
