package com.ciastek.library.remote.books.add

import com.ciastek.library.common.StateRepository
import javax.inject.Inject

class BookStateRepository @Inject constructor(): StateRepository<BookState> {

    private val states = mutableListOf<BookState>()

    override fun add(state: BookState) {
        states.add(state)
    }

    override fun clear() {
        states.clear()
    }

    override fun getLast(): BookState =
        states.last()

    override fun findLast(predicate: (BookState) -> Boolean): BookState? =
            states.findLast(predicate)

    override fun isEmpty(): Boolean =
            states.isEmpty()
}
