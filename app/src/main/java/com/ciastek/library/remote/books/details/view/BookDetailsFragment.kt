package com.ciastek.library.remote.books.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciastek.library.R

class BookDetailsFragment: Fragment() {

    companion object {

        const val BOOK_ID = "book_id"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.book_details_fragment, container, false)

    override fun onStart() {
        super.onStart()

        val id = requireArguments().get(BOOK_ID)

        Toast.makeText(context,
                       "Opened details for book with id: $id",
                       Toast.LENGTH_SHORT).show()
    }
}
