package com.ciastek.library.user.authors.repository

import com.ciastek.library.di.BackgroundScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class RoomUserAuthorsRepository @Inject constructor(private val authorDao: AuthorDao,
                                                    @BackgroundScheduler private val backgroundScheduler: Scheduler) : UserAuthorsRepository {

    override fun getAuthors(): Single<List<AuthorEntity>> =
            authorDao.getAllAuthors()
                    .subscribeOn(backgroundScheduler)

    override fun addAuthor(author: AuthorEntity): Completable =
            authorDao.insertAuthor(author)
                    .subscribeOn(backgroundScheduler)

    override fun removeAuthor(author: AuthorEntity) =
            authorDao.deleteAuthor(author)
                    .subscribeOn(backgroundScheduler)

    override fun isAuthorInFavourites(authorId: Long): Single<Boolean> =
            authorDao.countAuthor(authorId)
                    .map { it > 0 }
                    .subscribeOn(backgroundScheduler)
}