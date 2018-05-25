package com.ciastek.library.view

import android.app.Activity.RESULT_OK
import android.content.Intent
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

        presenter = BooksPresenter()
        presenter.attachView(this)

        add_fab.setOnClickListener {
            presenter.newBookButtonClicked()
        }

        booksAdapter = BooksAdapter()

        books_recycler_view.adapter = booksAdapter
        books_recycler_view.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(books_recycler_view.context,
                (books_recycler_view.layoutManager as LinearLayoutManager).orientation)
        books_recycler_view.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        booksAdapter.addBook(Book("Kotlin in Action", "D. Jemerov"))
        booksAdapter.addBook(Book("Effective Java", "J. Bloch"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_BOOK_REQUEST && resultCode == RESULT_OK) {
            val book = data.getParcelableExtra<Book>(CreateBookFragment.NEW_BOOK)

            presenter.newBookReceived(book)
        }
    }

    override fun startNewBookActivity() {
        startActivityForResult(CreateBookActivity.getIntent(this.context!!), CREATE_BOOK_REQUEST)
    }

    override fun addBook(book: Book) {
        booksAdapter.addBook(book)
    }

    private companion object {
        private const val CREATE_BOOK_REQUEST = 1
    }
}