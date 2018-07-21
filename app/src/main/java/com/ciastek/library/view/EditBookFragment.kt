package com.ciastek.library.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.ciastek.library.R
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.fragment_create_details_book.*

class EditBookFragment : Fragment() {
    private lateinit var book: Book

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

        book = arguments!!.getParcelable(BOOK_TO_EDIT)
        is_read.isChecked = book.isRead
        title_editText.setText(book.title)
        author_editText.setText(book.author)
        isbn_editText.setText(book.isbn)
    }

    companion object {
        private const val BOOK_TO_EDIT = "Book to edit"
        fun newInstance(book: Book) = EditBookFragment().apply {
            arguments = Bundle().apply { putParcelable(BOOK_TO_EDIT, book) }
        }
    }
}
