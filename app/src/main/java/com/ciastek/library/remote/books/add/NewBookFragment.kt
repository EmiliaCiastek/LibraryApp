package com.ciastek.library.remote.books.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.ciastek.library.R
import com.ciastek.library.common.showErrorMessage
import com.ciastek.library.remote.books.add.BookState.*
import com.ciastek.library.remote.books.add.di.NewBookComponent
import com.jakewharton.rxbinding3.widget.selectionEvents
import com.jakewharton.rxbinding3.widget.textChanges
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_new_book.*
import kotlinx.android.synthetic.main.fragment_new_book.book_description
import kotlinx.android.synthetic.main.fragment_new_book.book_title
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewBookFragment : Fragment(), NewBookContract.View {

    @Inject
    lateinit var presenter: NewBookContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_new_book, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
    }

    private fun injectDependencies() {
        NewBookComponent.create(requireContext())
                .inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_book_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStart() {
        presenter.bindIntents(this)

        super.onStart()
    }

    override fun onStop() {
        presenter.unbindIntents()

        super.onStop()
    }

    override fun renderBookState(state: BookState) {
        when (state) {
            is EmptyState -> renderEmptyBook(state.authors)
            is ErrorState -> renderErrorState(state.message)
            is LoadingState -> renderLoadingSate()
            is EditedState -> renderEditedBook(state.title, state.description, state.coverUrl, state.authorPickedPosition)
        }
    }

    override fun authorPickedIntent(): Observable<Int> =
        author_spinner.selectionEvents()
                .skipInitialValue()
                .map { it.view.selectedItemPosition }

    override fun coverUrlChangedIntent(): Observable<String> =
        book_cover_url.textChanges()
                .skipInitialValue()
                .map { it.toString() }
                .debounce(500, TimeUnit.MILLISECONDS)
                .doOnNext { Log.d("Cover:", it.toString()) }

    private fun renderErrorState(message: String) {
        progress.visibility = View.GONE

        showErrorMessage(requireContext(), message)
    }

    private fun renderEmptyBook(authors: List<String>) {
        progress.visibility = View.GONE

        val dataAdapter = ArrayAdapter<String>(requireContext(),
                                               android.R.layout.simple_spinner_item,
                                               authors)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        author_spinner.adapter = dataAdapter
    }

    private fun renderEditedBook(title: String,
                                 description: String,
                                 coverUrl: String,
                                 authorPickedPosition: Int) {
        if(author_spinner.selectedItemPosition != authorPickedPosition) {
            author_spinner.setSelection(authorPickedPosition)
        }

        if(book_title.text.toString() != title) {
            book_title.setText(title)
        }

        if(book_description.text.toString() != description) {
            book_description.setText(description)
        }

        if(book_cover_url.text.toString() != coverUrl) {
            book_cover_url.setText(coverUrl)
        }

        if(coverUrl.isNotEmpty()) {
            Picasso.get()
                    .load(coverUrl)
                    .placeholder(R.drawable.cover_placeholder)
                    .error(R.drawable.ic_sad)
                    .into(book_cover_thumbnail)
        }
    }

    private fun renderLoadingSate() {
        progress.visibility = View.VISIBLE
    }
}