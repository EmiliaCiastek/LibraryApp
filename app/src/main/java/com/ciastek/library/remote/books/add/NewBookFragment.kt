package com.ciastek.library.remote.books.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ciastek.library.R
import com.ciastek.library.common.showErrorMessage
import com.ciastek.library.remote.books.add.BookState.*
import com.ciastek.library.remote.books.add.di.NewBookComponent
import com.ciastek.library.remote.books.add.di.NewBookRxModule.CancelButton
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding3.widget.selectionEvents
import com.jakewharton.rxbinding3.widget.textChanges
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_new_book.book_cover_thumbnail as coverThumbnail
import kotlinx.android.synthetic.main.fragment_new_book.book_description as bookDescription
import kotlinx.android.synthetic.main.fragment_new_book.book_title as bookTitle
import kotlinx.android.synthetic.main.fragment_new_book.book_cover_url as bookCoverUrl
import kotlinx.android.synthetic.main.fragment_new_book.author_spinner as authorSpinner
import kotlinx.android.synthetic.main.fragment_new_book.progress
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewBookFragment : Fragment(), NewBookContract.View {

    companion object {

        private const val DEBOUNCE_TIME = 500L
        private const val COVER_TAG = "COVER"
    }

    @Inject
    lateinit var presenter: NewBookContract.Presenter

    @Inject
    @CancelButton
    lateinit var cancelButtonSubject: Subject<Unit>

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_book_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancel -> {
                cancelButtonSubject.onNext(Unit)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
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
            is EditedState -> renderEditedBook(state.title,
                                               state.description,
                                               state.coverUrl,
                                               state.authorPickedPosition)
            is CanceledState -> closeForm()
        }
    }

    override fun authorPickedIntent(): Observable<Int> =
            authorSpinner.selectionEvents()
                    .skipInitialValue()
                    .map { it.view.selectedItemPosition }

    override fun coverUrlChangedIntent(): Observable<String> =
            bookCoverUrl.textChanges()
                    .skipInitialValue()
                    .map { it.toString() }
                    .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()

    override fun titleChangedIntent(): Observable<String> =
            bookTitle.textChanges()
                    .skipInitialValue()
                    .map { it.toString() }
                    .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()


    override fun descriptionChangedIntent(): Observable<String> =
            bookDescription.textChanges()
                    .skipInitialValue()
                    .map { it.toString() }
                    .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()

    override fun cancelFormIntent(): Observable<Unit> =
            cancelButtonSubject.hide()
                    .doOnNext { Log.d("Lalalala", "cancel clicked!!!!") }

    private fun injectDependencies() {
        NewBookComponent.create(requireContext())
                .inject(this)
    }

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
        authorSpinner.adapter = dataAdapter
    }

    private fun renderEditedBook(title: String,
                                 description: String,
                                 coverUrl: String,
                                 authorPickedPosition: Int) {
        authorSpinner.setSelection(authorPickedPosition)
        bookTitle.updateText(title)
        bookDescription.updateText(description)
        bookCoverUrl.updateText(coverUrl)
        updateCoverThumbnail(coverUrl)
    }

    private fun updateCoverThumbnail(coverUrl: String) {
        if (coverUrl.isNotEmpty()) {
            Picasso.get()
                    .load(coverUrl)
                    .tag(COVER_TAG)
                    .placeholder(coverThumbnail.drawable)
                    .error(R.drawable.ic_sad)
                    .into(coverThumbnail)
        }
    }

    private fun renderLoadingSate() {
        progress.visibility = View.VISIBLE
    }

    private fun closeForm() {
        findNavController().navigateUp()
    }

    private fun TextInputEditText.updateText(text: String) {
        setText(text)
        setSelection(text.length)
    }
}
