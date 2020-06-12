package com.ciastek.library.remote.books.details.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.ciastek.library.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.android.synthetic.main.details_header_layout.*

class BookDetailsActivity : AppCompatActivity() {

    private val args: BookDetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar_layout.title = args.bookTitle
        header_title.text = args.bookTitle
        header_subtitle.text = args.bookAuthor

        findViewById<ExtendedFloatingActionButton>(R.id.fab).addOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                //TODO: Add to user library
            }
        }

    }
}