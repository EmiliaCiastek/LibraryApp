package com.ciastek.library.remote.books.add

sealed class BookState {

    data class EmptyState(val authors: List<String>) : BookState()

    data class ErrorState(val message: String) : BookState()

    data class EditedState(val title: String,
                      val description: String,
                      val coverUrl: String,
                      val authorPickedPosition: Int) : BookState()

    object LoadingState : BookState()

    object CanceledState: BookState()

    object SavedState: BookState()
}