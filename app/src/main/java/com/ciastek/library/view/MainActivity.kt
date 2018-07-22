package com.ciastek.library.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ciastek.library.R
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.activity_books.*

class MainActivity : AppCompatActivity(), BooksListFragment.OnBookSelectedListener, EditBookFragment.OnBookEditedListener {
    private var isSinglePane = true
    private lateinit var listFragment: BooksListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        setSupportActionBar(toolbar)

        isSinglePane = fragment_container != null

        if (isSinglePane) {
            listFragment = BooksListFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, listFragment, LIST_FRAGMENT_TAG)
                    .commit()
        } else {
            listFragment = supportFragmentManager.findFragmentById(R.id.books_list_fragment) as BooksListFragment
        }

        listFragment.setOnBookSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.list_activity_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)

        if (item?.itemId == R.id.add_book_button) {
            startActivityForResult(CreateBookActivity.newInstance(this), CREATE_BOOK_REQUEST_CODE)
            return true
        }

        return false
    }

    override fun onBookSelected(book: Book) {
        val containerId = if (isSinglePane) R.id.fragment_container else R.id.details_create_container

        supportFragmentManager.beginTransaction()
                .replace(containerId, EditBookFragment.newInstance(book, this), DETAILS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CREATE_BOOK_REQUEST_CODE) {
            val book = data!!.getParcelableExtra<Book>(CreateBookActivity.CREATED_BOOK)
            listFragment.addBook(book)

            if (!isSinglePane) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.details_create_container, EditBookFragment.newInstance(book, this), DETAILS_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit()
            }
        }
    }

    override fun onBookSaved(book: Book) {
        listFragment.updateBook(book)

        if (isSinglePane)
            supportFragmentManager.popBackStack()
    }

    override fun onBookRemoved(book: Book) {
        listFragment.removeBook(book)

        if (isSinglePane)
            supportFragmentManager.popBackStack()
        else {
            supportFragmentManager.beginTransaction()
                    .remove(supportFragmentManager.findFragmentByTag(DETAILS_FRAGMENT_TAG))
                    .commit()
        }
    }

    private companion object {
        private const val LIST_FRAGMENT_TAG = "ListFragment"
        private const val DETAILS_FRAGMENT_TAG = "EditFragment"
        private const val CREATE_BOOK_REQUEST_CODE = 0
    }
}
