package com.ciastek.library.remote.authors.details.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.authors.details.reporitory.AuthorDetailsRepository
import com.ciastek.library.remote.authors.details.view.AuthorDetailsViewModel
import io.reactivex.Scheduler

class AuthorDetailsViewModelFactory (private val authorDetailsRepository: AuthorDetailsRepository,
                                     private val schedulerUi: Scheduler): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            AuthorDetailsViewModel(authorDetailsRepository, schedulerUi) as T
}