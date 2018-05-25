package com.ciastek.library.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciastek.library.BooksContracts
import com.ciastek.library.BooksContracts.Presenter
import com.ciastek.library.R
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.LibraryDatabase
import com.ciastek.library.presenter.BooksPresenter
import kotlinx.android.synthetic.main.fragment_books.*

class BooksListFragment : Fragment(), BooksContracts.View {
    private lateinit var booksAdapter: BooksAdapter
    private lateinit var presenter: Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_fab.setOnClickListener {
            presenter.newBookButtonClicked()
        }

        booksAdapter = BooksAdapter()

        books_recycler_view.adapter = booksAdapter
        books_recycler_view.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(books_recycler_view.context,
                (books_recycler_view.layoutManager as LinearLayoutManager).orientation)
        books_recycler_view.addItemDecoration(dividerItemDecoration)

        presenter = BooksPresenter(LibraryDatabase.getInstance(context!!).bookDao())
        presenter.attachView(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun startNewBookActivity() {
        startActivity(CreateBookActivity.getIntent(this.context!!))
    }

    override fun addBook(book: Book) {
        booksAdapter.addBook(book)
    }

    override fun setBooks(books: List<Book>) {
        booksAdapter.setBooks(books)
    }
}
