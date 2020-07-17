package com.ciastek.library.user.books.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciastek.library.R
import com.ciastek.library.common.books.BookModel
import com.ciastek.library.common.books.BooksAdapter
import com.ciastek.library.common.showErrorMessage
import com.ciastek.library.user.books.di.UserBooksComponent
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_user_books.user_books_list as userBooksList

class UserBooksFragment: Fragment(), UserBooksMVP.View {

    @Inject
    lateinit var presenter: UserBooksMVP.Presenter

    private lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return  inflater.inflate(R.layout.fragment_user_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        booksAdapter = BooksAdapter {  }

        userBooksList.apply {
            adapter = booksAdapter
            layoutManager = LinearLayoutManager(context)
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

    override fun showBooks(books: List<BookModel>) {
        booksAdapter.setBooks(books)
    }

    override fun showError(message: String?) {
        showErrorMessage(context, message)
    }

    private fun injectDependencies() {
        UserBooksComponent.create(requireContext())
                .inject(this)
    }
}
