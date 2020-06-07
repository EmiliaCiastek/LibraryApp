package com.ciastek.library.remote.books.details.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciastek.library.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_details_fragment.*


class BookDetailsFragment: Fragment() {

    companion object {

        const val BOOK_ID = "book_id"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.book_details_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView5.movementMethod = ScrollingMovementMethod()
    }

    override fun onStart() {
        super.onStart()

        val id = requireArguments().get(BOOK_ID)

        Toast.makeText(context,
                       "Opened details for book with id: $id",
                       Toast.LENGTH_SHORT).show()

        Picasso.get().load("https://tessgerritsenbooklist.com/wp-content/uploads/2014/01/Tess-Gerritsen-Gravity1.jpg")
                .placeholder(R.drawable.cover_placeholder)
                .into(imageView5)
    }
}
