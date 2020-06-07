package com.ciastek.library.remote.books.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciastek.library.R
import com.ciastek.library.remote.books.details.view.BookDetailsActivity.Companion.BOOK
import com.ciastek.library.remote.books.list.di.BooksListComponent
import com.ciastek.library.showErrorMessage
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_remote_books.books_list as booksList

class RemoteBooksFragment : Fragment() {

    @Inject
    lateinit var booksViewModel: BooksViewModel

    private lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_remote_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        booksAdapter = BooksAdapter { navigateToBookDetails(it) }

        booksList.apply {
            adapter = booksAdapter
            layoutManager = LinearLayoutManager(context)
        }

        booksViewModel.books.observe(viewLifecycleOwner, Observer {
            booksAdapter.setBooks(it)
            if (it.isEmpty()) {
                showErrorMessage(context)
                //TODO: Add swipe up to refresh
            }
        })
    }

    override fun onStart() {
        super.onStart()

        booksViewModel.fetchBooks()
    }

    private fun injectDependencies() {
        BooksListComponent.create(requireContext())
                .inject(this)
    }

    private fun navigateToBookDetails(book: BookModel) {
        val extras = Bundle()
                .apply { putParcelable(BOOK, book) }
        requireActivity().findNavController(R.id.main_nav_host_fragment)
                .navigate(R.id.book_details_activity, extras)
    }
}
