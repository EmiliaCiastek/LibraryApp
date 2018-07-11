package com.ciastek.library.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ciastek.library.R
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.activity_books.*

class BooksListActivity : AppCompatActivity(), CreateBookFragment.OnBookAddedListener {
    private var isDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        setSupportActionBar(toolbar)

        isDualPane = details_create_container != null

        if (!isDualPane) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, BooksListFragment(), LIST_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit()

            add_fab!!.setOnClickListener {
                it.visibility = View.INVISIBLE

                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CreateBookFragment())
                        .commit()
            }
        }
    }

    override fun onBookAdded(book: Book) {
        val listFragment = (supportFragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG)) as BooksListFragment
        listFragment.addBook(book)
        if (!isDualPane) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, listFragment)
                    .commit()
        }
    }

    private companion object {
        private const val LIST_FRAGMENT_TAG = "ListFragment"
    }
}
