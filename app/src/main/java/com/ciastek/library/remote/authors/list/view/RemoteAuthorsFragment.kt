package com.ciastek.library.remote.authors.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciastek.library.R
import com.ciastek.library.common.authors.AuthorModel
import com.ciastek.library.common.authors.AuthorsAdapter
import com.ciastek.library.remote.RemoteLibraryFragmentDirections.Companion.actionShowRemoteAuthorDetails
import com.ciastek.library.remote.authors.list.di.AuthorsListComponent
import com.ciastek.library.common.showErrorMessage
import kotlinx.android.synthetic.main.fragment_remote_authors.refreshLayout
import kotlinx.android.synthetic.main.fragment_remote_authors.authorsList
import javax.inject.Inject

class RemoteAuthorsFragment : Fragment() {

    @Inject
    lateinit var authorsViewModel: AuthorsViewModel

    private lateinit var authorsAdapter: AuthorsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_remote_authors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        authorsAdapter = AuthorsAdapter {
            navigateToAuthorDetails(it)
        }

        authorsList.apply {
            adapter = authorsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        authorsViewModel.authors.observe(viewLifecycleOwner, Observer {
            refreshLayout.isRefreshing = false
            authorsAdapter.setAuthors(it)
            if (it.isEmpty()) {
                showErrorMessage(context)
            }
        })

        refreshLayout.setOnRefreshListener { authorsViewModel.fetchAuthors() }
    }

    override fun onStart() {
        super.onStart()

        authorsViewModel.fetchAuthors()
    }

    private fun injectDependencies() {
        AuthorsListComponent.create(requireContext())
                .inject(this)
    }

    private fun navigateToAuthorDetails(author: AuthorModel) {
        val action = actionShowRemoteAuthorDetails("${author.lastName} ${author.name}", author.id)
        requireActivity().findNavController(R.id.main_nav_host_fragment)
                .navigate(action)
    }
}
