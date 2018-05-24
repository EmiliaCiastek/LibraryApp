package com.ciastek.library.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciastek.library.R
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivityFragment : Fragment() {
    private lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_fab.setOnClickListener {
            startActivity(CreateBookActivity.getIntent(this.context!!))
        }

        booksAdapter = BooksAdapter()

        books_recycler_view.adapter = booksAdapter
        books_recycler_view.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(books_recycler_view.context,
                (books_recycler_view.layoutManager as LinearLayoutManager).orientation)
        books_recycler_view.addItemDecoration(dividerItemDecoration)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        booksAdapter.addBook(Book("Kotlin in Action", "D. Jemerov"))
        booksAdapter.addBook(Book("Effective Java", "J. Bloch"))
    }
}
