package com.ciastek.library.remote.authors.repository

import io.reactivex.Single

class FakeRemoteAuthorsRepository : RemoteAuthorsRepository {

    private val authors = listOf(
            Author("Tess", "Gerritsen", 1),
            Author("John", "Green", 2),
            Author("Jennifer", "Niven", 3),
            Author("George R.R.", "Martin", 4),
            Author("Robert C.", "Martin", 5)
    ).sortedBy { it.lastName }

    override fun getAuthors(): Single<List<Author>> =
            Single.just(authors)
}