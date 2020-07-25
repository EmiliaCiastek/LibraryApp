package com.ciastek.library.remote.books.add.di

import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Qualifier

@Module
class NewBookRxModule {

    private val cancelButtonSubject = PublishSubject.create<Unit>()
    private val saveButtonSubject = PublishSubject.create<Unit>()

    @Provides
    @CancelButton
    fun provideCancelButtonSubject(): Subject<Unit> = cancelButtonSubject

    @Provides
    @SaveButton
    fun provideSaveButtonSubject(): Subject<Unit> = saveButtonSubject

    @Qualifier
    annotation class CancelButton

    @Qualifier
    annotation class SaveButton
}