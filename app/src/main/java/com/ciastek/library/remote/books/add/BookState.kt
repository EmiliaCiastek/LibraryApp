package com.ciastek.library.remote.books.add

sealed class BookState {

    class EmptyState(val authors: List<String>) : BookState()

    class ErrorState(val message: String) : BookState()

    class EditedState(val title: String,
                      val description: String,
                      val coverUrl: String,
                      val authorPickedPosition: Int) : BookState()

    object LoadingState : BookState()
}