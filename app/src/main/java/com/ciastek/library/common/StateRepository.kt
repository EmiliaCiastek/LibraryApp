package com.ciastek.library.common

interface StateRepository<T> {

    fun add(state: T)

    fun clear()

    fun getLast(): T

    fun findLast(predicate: (T) -> Boolean): T?

    fun isEmpty(): Boolean
}
