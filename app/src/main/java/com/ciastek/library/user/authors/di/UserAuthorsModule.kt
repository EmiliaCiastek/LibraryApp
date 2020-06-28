package com.ciastek.library.user.authors.di

import com.ciastek.library.user.authors.repository.RoomUserAuthorsRepository
import com.ciastek.library.user.authors.repository.UserAuthorsRepository
import com.ciastek.library.user.authors.view.UserAuthorsMVP
import com.ciastek.library.user.authors.view.UserAuthorsPresenter
import dagger.Binds
import dagger.Module

@Module
interface UserAuthorsModule {

    @Binds
    fun bindUserAuthorsPresenter(presenter: UserAuthorsPresenter): UserAuthorsMVP.Presenter
}

@Module
interface UserAuthorsRepositoryModule {

    @Binds
    fun bindUserAuthorsRepository(repository: RoomUserAuthorsRepository): UserAuthorsRepository
}
