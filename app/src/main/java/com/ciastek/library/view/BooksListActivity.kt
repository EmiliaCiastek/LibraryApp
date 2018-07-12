package com.ciastek.library.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ciastek.library.R
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.activity_books.*

class BooksListActivity : AppCompatActivity(), CreateBookFragment.OnBookAddedListener, BooksListFragment.OnBookSelectedListener {
    private var isDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        setSupportActionBar(toolbar)

        isDualPane = details_create_container != null
        if (savedInstanceState == null) {
            if (!isDualPane) {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, BooksListFragment(), LIST_FRAGMENT_TAG)
                        .commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.list_activity_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)

        if (item?.itemId == R.id.add_book_button) {
            if (!isDualPane) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CreateBookFragment())
                        .addToBackStack(null)
                        .commit()
            } else {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.details_create_container, CreateBookFragment(), CREATE_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit()
            }

            item.isVisible = false

            return true
        }

        return false
    }

    override fun onBookAdded(book: Book) {
        val listFragment = if (isDualPane) {
            (supportFragmentManager.findFragmentById(R.id.books_list_fragment)) as BooksListFragment
        } else {
            (supportFragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG)) as BooksListFragment
        }

        listFragment.addBook(book)
        if (!isDualPane) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, listFragment)
                    .commit()
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.details_create_container, EditBookFragment.newInstance(book))
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onBookSelected(book: Book) {
        val containerId = if (isDualPane) R.id.details_create_container else R.id.fragment_container

        supportFragmentManager.beginTransaction()
                .replace(containerId, EditBookFragment.newInstance(book))
                .addToBackStack(null)
                .commit()
    }

    private companion object {
        private const val LIST_FRAGMENT_TAG = "ListFragment"
        private const val CREATE_FRAGMENT_TAG = "CreateFragment"
    }
}
