package com.ciastek.library.remote.authors.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.authors.repository.RemoteAuthorsRepository
import com.ciastek.library.remote.authors.view.AuthorsViewModel
import io.reactivex.Scheduler

class AuthorsViewModelFactory(private val authorsRepository: RemoteAuthorsRepository,
                              private val schedulerUi: Scheduler): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        AuthorsViewModel(authorsRepository, schedulerUi) as T
}
