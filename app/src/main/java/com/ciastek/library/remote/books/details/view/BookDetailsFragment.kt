package com.ciastek.library.remote.books.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ciastek.library.R
import com.ciastek.library.remote.books.details.di.BookDetailsComponent
import com.ciastek.library.showErrorMessage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_book_details.*
import javax.inject.Inject

class BookDetailsFragment : Fragment() {

    private val args: BookDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var bookDetailsViewModel: BookDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        injectDependencies()

        bookDetailsViewModel.bookDetails.observe(viewLifecycleOwner, Observer { bookDetails ->
            if(bookDetails.isEmpty()) {
                showErrorMessage(context)
            }

            bookDetails.show()
        })

        favourite_button.addOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                bookDetailsViewModel.addBookToUserLibrary()
            } else {
                bookDetailsViewModel.removeBookFromUserLibrary()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_book_details, menu)
    }

    override fun onStart() {
        super.onStart()

        bookDetailsViewModel.fetchBook(args.bookId)
    }

    private fun BookDetailsView.show() {
        book_title.text = title
        author_name.text = author
        book_rate.rating = rating.toFloat()
        favourite_button.isChecked = isFavourite
        if(description.isNotEmpty()) {
            book_description.text = description
        }
        if(coverUrl.isNotEmpty()) {
            Picasso.get()
                    .load(coverUrl)
                    .placeholder(R.drawable.cover_placeholder)
                    .into(book_cover)
        }
    }

    private fun injectDependencies() {
        BookDetailsComponent.create(requireContext())
                .inject(this)
    }
}