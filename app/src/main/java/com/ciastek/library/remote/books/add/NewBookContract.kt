package com.ciastek.library.remote.books.add

import io.reactivex.Observable

interface NewBookContract {

    interface Presenter {

        fun bindIntents(view: View)

        fun unbindIntents()
    }

    interface View {

        fun renderBookState(state: BookState)

        fun authorPickedIntent(): Observable<Int>

        fun coverUrlChangedIntent(): Observable<String>

        fun titleChangedIntent(): Observable<String>

        fun descriptionChangedIntent(): Observable<String>
    }
}
