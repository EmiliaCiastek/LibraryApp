package com.ciastek.library.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciastek.library.BooksContract
import com.ciastek.library.BooksContract.Presenter
import com.ciastek.library.R
import com.ciastek.library.di.components.BooksComponent
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.fragment_books.*
import javax.inject.Inject

class BooksListFragment : Fragment(), BooksContract.View {
    @Inject
    lateinit var presenter: Presenter
    private lateinit var booksAdapter: BooksAdapter
    private var listener: OnBookSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnBookSelectedListener) {
            listener = context
        } else {
            throw IllegalArgumentException("$context should implement BooksListFragment.OnBookSelectedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        booksAdapter = BooksAdapter { position -> listener?.onBookSelected(booksAdapter.getBook(position)) }

        books_recycler_view.adapter = booksAdapter
        books_recycler_view.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(books_recycler_view.context,
                (books_recycler_view.layoutManager as LinearLayoutManager).orientation)
        books_recycler_view.addItemDecoration(dividerItemDecoration)

        presenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun addBook(book: Book) {
        booksAdapter.addBook(book)
    }

    override fun setBooks(books: List<Book>) {
        booksAdapter.setBooks(books)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun injectDependencies() {
        BooksComponent.create(context!!).inject(this)
    }

    interface OnBookSelectedListener {
        fun onBookSelected(book: Book)
    }
}
