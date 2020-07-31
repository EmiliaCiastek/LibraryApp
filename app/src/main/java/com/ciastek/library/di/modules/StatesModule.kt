package com.ciastek.library.di.modules

import com.ciastek.library.common.StateRepository
import com.ciastek.library.remote.books.add.BookState
import com.ciastek.library.remote.books.add.BookStateRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class StatesModule {

    @Binds
    @Singleton
    abstract fun bindBookStateRepository(repository: BookStateRepository): StateRepository<BookState>
}
