package com.ciastek.library.remote.authors.details.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.authors.details.reporitory.AuthorDetailsRepository
import com.ciastek.library.remote.authors.details.view.AuthorDetailsViewModel
import com.ciastek.library.user.authors.repository.UserAuthorsRepository
import io.reactivex.Scheduler

class AuthorDetailsViewModelFactory (private val authorDetailsRepository: AuthorDetailsRepository,
                                     private val userAuthorsRepository: UserAuthorsRepository,
                                     private val schedulerUi: Scheduler): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            AuthorDetailsViewModel(authorDetailsRepository, userAuthorsRepository, schedulerUi) as T
}