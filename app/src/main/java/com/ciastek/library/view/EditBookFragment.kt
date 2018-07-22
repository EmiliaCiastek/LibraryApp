package com.ciastek.library.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.ciastek.library.EditBookContract
import com.ciastek.library.R
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.LibraryDatabase
import com.ciastek.library.presenter.EditBookPresenter
import kotlinx.android.synthetic.main.fragment_create_details_book.*

class EditBookFragment : Fragment(), EditBookContract.View {
    private lateinit var presenter: EditBookContract.Presenter
    private var listener: EditBookFragment.OnBookEditedListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_edit_book, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_details_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save_book_button.setOnClickListener {
            val title = title_editText.text.toString()
            val author = author_editText.text.toString()
            val isbn = isbn_editText.text.toString()
            val isRead = is_read.isChecked
            presenter.onSaveBookClicked(title, author, isbn, isRead)
        }
        presenter = EditBookPresenter(arguments!!.getParcelable(BOOK_TO_EDIT), LibraryDatabase.getInstance(context!!).bookDao())
        presenter.attachView(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_book) {
            presenter.onRemoveBookClicked()
        }

        return false
    }

    override fun bookSaved(book: Book) {
        listener?.onBookSaved(book)
    }

    override fun bookDeleted(book: Book) {
        listener?.onBookRemoved(book)
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

    interface OnBookEditedListener {
        fun onBookSaved(book: Book)

        fun onBookRemoved(book: Book)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        private const val BOOK_TO_EDIT = "Book to edit"
        fun newInstance(book: Book, listener: OnBookEditedListener) = EditBookFragment().apply {
            arguments = Bundle().apply { putParcelable(BOOK_TO_EDIT, book) }
            this.listener = listener
        }
    }
}
