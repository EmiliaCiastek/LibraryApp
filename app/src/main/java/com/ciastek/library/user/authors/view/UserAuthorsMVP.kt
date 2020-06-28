package com.ciastek.library.user.authors.view

import com.ciastek.library.common.authors.AuthorModel

interface UserAuthorsMVP {

    interface Presenter {

        fun attach(view: View)

        fun detach()
    }

    interface View {
        fun showAuthors(authors: List<AuthorModel>)
    }
}
