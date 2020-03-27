package com.ciastek.library.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ciastek.library.R
import com.ciastek.library.model.Book
import com.ciastek.library.remote.view.RemoteLibraryActivity
import kotlinx.android.synthetic.main.activity_books.*

class MainActivity : AppCompatActivity(), BooksListFragment.OnBookSelectedListener, EditBookFragment.OnBookChangedListener {
    private var isSinglePane = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        isSinglePane = edit_book_container == null

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, BooksListFragment(), LIST_FRAGMENT_TAG)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.list_activity_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        return when (item.itemId) {
            R.id.add_book_button -> {
                startActivityForResult(CreateBookActivity.newInstance(this), CREATE_BOOK_REQUEST_CODE)
                true
            }
            R.id.show_remote_library -> {
                startActivity(RemoteLibraryActivity.newIntent(this))
                true
            }
            else -> false
        }
    }

    override fun onBookSelected(book: Book) {
        val containerId = if (isSinglePane) R.id.fragment_container else R.id.edit_book_container

        supportFragmentManager.beginTransaction()
                .replace(containerId, EditBookFragment.newInstance(book), DETAILS_FRAGMENT_TAG)
                .apply {
                    if (isSinglePane)
                        addToBackStack(null)
                }
                .commit()
    }

    override fun onBookSaved() {
        if (isSinglePane)
            supportFragmentManager.popBackStack()
    }

    override fun onBookRemoved() {
        if (isSinglePane) {
            supportFragmentManager.popBackStack()
        } else {
            supportFragmentManager.beginTransaction()
                    .remove(getEditBookFragment())
                    .commit()
        }
    }

    private fun getEditBookFragment(): EditBookFragment = supportFragmentManager.findFragmentByTag(DETAILS_FRAGMENT_TAG) as EditBookFragment

    private companion object {
        private const val LIST_FRAGMENT_TAG = "ListFragment"
        private const val DETAILS_FRAGMENT_TAG = "EditFragment"
        private const val CREATE_BOOK_REQUEST_CODE = 0
    }
}
