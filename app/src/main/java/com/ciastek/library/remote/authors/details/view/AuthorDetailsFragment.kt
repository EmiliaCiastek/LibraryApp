package com.ciastek.library.remote.authors.details.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciastek.library.R
import com.ciastek.library.remote.RemoteLibraryFragmentDirections
import com.ciastek.library.remote.authors.details.di.AuthorDetailsComponent
import com.ciastek.library.common.books.BookModel
import com.ciastek.library.common.books.BooksAdapter
import com.ciastek.library.showErrorMessage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.author_details_fragment.*
import javax.inject.Inject

class AuthorDetailsFragment : Fragment() {

    private val args: AuthorDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var authorDetailsViewModel: AuthorDetailsViewModel

    private lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.author_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        injectDependencies()
        booksAdapter = BooksAdapter {
            navigateToBookDetails(it)
        }
        authors_books_list.apply {
            adapter = booksAdapter
            layoutManager = LinearLayoutManager(context)
        }

        authorDetailsViewModel.authorDetails.observe(viewLifecycleOwner, Observer { authorDetails ->
            if (authorDetails.isEmpty()) {
                showErrorMessage(context)
            }

            authorDetails.show()
        })
    }

    override fun onStart() {
        super.onStart()

        authorDetailsViewModel.getAuthorDetails(args.authorId)
    }

    private fun AuthorDetailsView.show() {
        showBooks()
        showBirth()
        showDeath()
        showWebsite()
        showGenres()
        showDescription()
        showPhoto()
    }

    private fun AuthorDetailsView.showBooks() {
        if (books.isEmpty()) {
            authors_books_header.visibility = View.GONE
            authors_books_list.visibility = View.GONE
            no_books_info.visibility = View.VISIBLE
        } else {
            booksAdapter.setBooks(books)
        }
    }

    private fun AuthorDetailsView.showDescription() {
        if (description.isNotBlank()) {
            author_description.text = description
        }
    }

    private fun AuthorDetailsView.showPhoto() {
        if (photoUrl.isNotBlank()) {
            Picasso.get()
                    .load(photoUrl)
                    .placeholder(R.drawable.ic_author)
                    .into(author_photo)
        }
    }

    private fun AuthorDetailsView.showGenres() {
        if (genres.isBlank()) {
            author_genres.visibility = View.GONE
            genre_label.visibility = View.GONE
        } else {
            author_genres.text = genres
        }
    }

    private fun AuthorDetailsView.showWebsite() {
        if (website.isBlank()) {
            author_website.visibility = View.GONE
            website_label.visibility = View.GONE
        } else {
            author_website.text = website
            author_website.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW)
                                      .apply {
                                          data = Uri.parse(website)
                                      })
            }
        }
    }

    private fun AuthorDetailsView.showDeath() {
        if (deathDate.isBlank()) {
            author_death.visibility = View.GONE
            died_label.visibility = View.GONE
        } else {
            author_death.text = deathDate
        }
    }

    private fun AuthorDetailsView.showBirth() {
        if (birthDate.isBlank()) {
            author_birth.visibility = View.GONE
            born_label.visibility = View.GONE
        } else {
            author_birth.text = birthDate
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.author_detail_menu, menu)
    }

    private fun injectDependencies() {
        AuthorDetailsComponent.create(requireContext())
                .inject(this)
    }

    private fun navigateToBookDetails(book: BookModel) {
        val action = RemoteLibraryFragmentDirections.actionShowRemoteBookDetails(book.id,
                                                                                 book.title,
                                                                                 book.author)
        requireActivity().findNavController(R.id.main_nav_host_fragment)
                .navigate(action)
    }
}
