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
    private var listener: OnBookSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        booksAdapter = BooksAdapter { position -> listener?.onBookSelected(booksAdapter.getBook(position)) }

        books_recycler_view.adapter = booksAdapter
        books_recycler_view.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(books_recycler_view.context,
                (books_recycler_view.layoutManager as LinearLayoutManager).orientation)
        books_recycler_view.addItemDecoration(dividerItemDecoration)

        presenter = BooksPresenter(LibraryDatabase.getInstance(context!!).bookDao())
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun addBook(book: Book) {
        booksAdapter.addBook(book)
    }

    fun updateBook(book: Book) {
        booksAdapter.updateBook(book)
    }

    fun removeBook(book: Book) {
        booksAdapter.removeBook(book)
    }

    override fun setBooks(books: List<Book>) {
        booksAdapter.setBooks(books)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun setOnBookSelectedListener(listener: OnBookSelectedListener) {
        this.listener = listener
    }

    interface OnBookSelectedListener {
        fun onBookSelected(book: Book)
    }
}
