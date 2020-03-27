package com.ciastek.library.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import com.ciastek.library.EditBookContract
import com.ciastek.library.R
import com.ciastek.library.di.components.EditBookComponent
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.fragment_create_details_book.*
import javax.inject.Inject

class EditBookFragment : Fragment(), EditBookContract.View {
    @Inject
    lateinit var presenter: EditBookContract.Presenter
    private var listener: EditBookFragment.OnBookChangedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnBookChangedListener) {
            listener = context
        } else {
            throw IllegalArgumentException("$context should implement EditBookFragment.OnBookChangedListener")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_edit_book, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_details_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies(arguments!!.getParcelable(BOOK_TO_EDIT))

        save_book_button.setOnClickListener {
            val title = title_editText.text.toString()
            val author = author_editText.text.toString()
            val isbn = isbn_editText.text.toString()
            val isRead = is_read.isChecked
            presenter.onSaveBookClicked(title, author, isbn, isRead)
        }

        presenter.attachView(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_book) {
            presenter.onRemoveBookClicked()
        }

        return false
    }

    override fun bookSaved() {
        listener?.onBookSaved()
    }

    override fun bookDeleted() {
        listener?.onBookRemoved()
    }

    override fun setTitle(title: String) {
        title_editText.setText(title)
    }

    override fun setAuthor(author: String) {
        author_editText.setText(author)
    }

    override fun setIsbn(isbn: String) {
        isbn_editText.setText(isbn)
    }

    override fun isRead(isRead: Boolean) {
        is_read.isChecked = isRead
    }

    interface OnBookChangedListener {
        fun onBookSaved()

        fun onBookRemoved()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun injectDependencies(book: Book) {
        EditBookComponent.create(context!!, book)
                .inject(this)
    }

    companion object {
        private const val BOOK_TO_EDIT = "Book to edit"
        fun newInstance(book: Book) = EditBookFragment().apply {
            arguments = Bundle().apply { putParcelable(BOOK_TO_EDIT, book) }
        }
    }
}
