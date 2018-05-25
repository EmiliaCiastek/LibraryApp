package com.ciastek.library

interface Attachable<T> {
    fun attachView(view: T)

    fun detachView()
}