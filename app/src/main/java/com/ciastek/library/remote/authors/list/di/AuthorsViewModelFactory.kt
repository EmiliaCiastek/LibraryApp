package com.ciastek.library.remote.authors.list.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.authors.list.repository.AuthorsRepository
import com.ciastek.library.remote.authors.list.view.AuthorsViewModel
import io.reactivex.Scheduler

class AuthorsViewModelFactory(private val authorsRepository: AuthorsRepository,
                              private val schedulerUi: Scheduler): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        AuthorsViewModel(authorsRepository, schedulerUi) as T
}
