package com.ciastek.library.remote.books.details.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ciastek.library.R
import com.ciastek.library.remote.books.list.view.BookModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.android.synthetic.main.details_header_layout.*

class BookDetailsActivity : AppCompatActivity() {

    companion object {

        const val BOOK = "book"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        intent.extras?.getParcelable<BookModel>(BOOK)?.let { book ->
            toolbar_layout.title = book.title
            header_title.text = book.title
            header_subtitle.text = book.author
        }

        findViewById<ExtendedFloatingActionButton>(R.id.fab).addOnCheckedChangeListener { button, isChecked ->
            if(isChecked) {
                //TODO: Add to user library
            }
        }

    }
}