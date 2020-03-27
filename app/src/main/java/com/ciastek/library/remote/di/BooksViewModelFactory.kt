package com.ciastek.library.remote.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciastek.library.remote.repository.RemoteBooksRepository
import com.ciastek.library.remote.view.BooksViewModel

class BooksViewModelFactory(private val booksRepository: RemoteBooksRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        BooksViewModel(booksRepository) as T
}