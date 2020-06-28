package com.ciastek.library.user.authors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciastek.library.R
import com.ciastek.library.common.authors.AuthorModel
import com.ciastek.library.common.authors.AuthorsAdapter
import com.ciastek.library.user.authors.di.UserAuthorsComponent
import com.ciastek.library.user.authors.view.UserAuthorsMVP
import kotlinx.android.synthetic.main.fragment_user_authors.*
import javax.inject.Inject

class UserAuthorsFragment: Fragment(), UserAuthorsMVP.View {

    @Inject
    lateinit var presenter: UserAuthorsMVP.Presenter

    private lateinit var authorsAdapter: AuthorsAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_user_authors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        authorsAdapter = AuthorsAdapter {}

        user_authors_list.apply {
            adapter = authorsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.attach(this)
    }

    override fun onStop() {
        presenter.detach()

        super.onStop()
    }

    override fun showAuthors(authors: List<AuthorModel>) {
        authorsAdapter.setAuthors(authors)
    }

    private fun injectDependencies() {
        UserAuthorsComponent.create(requireContext())
                .inject(this)
    }
}
