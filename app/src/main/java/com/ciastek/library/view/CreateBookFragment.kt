package com.ciastek.library.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciastek.library.CreateBookContract
import com.ciastek.library.R
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.LibraryDatabase
import com.ciastek.library.presenter.CreateBookPresenter
import kotlinx.android.synthetic.main.fragment_create_book.*

class CreateBookFragment : Fragment(), CreateBookContract.View {
    private lateinit var presenter: CreateBookContract.Presenter
    private lateinit var errorDialog: AlertDialog
    private lateinit var listener: OnBookAddedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_book, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnBookAddedListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement CreateBookFragment.OnBookAddedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CreateBookPresenter(LibraryDatabase.getInstance(context!!).bookDao()) //TODO: add Dagger
        presenter.attachView(this)

        save_book_button.setOnClickListener {
            val book = Book(title = title_editText.text.toString(), author = author_editText.text.toString())
            book.isRead = is_read.isChecked

            presenter.saveBookButtonClicked(book)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun showError() {
        if (!::errorDialog.isInitialized) {
            errorDialog = AlertDialog.Builder(context!!)
                    .apply {
                        setMessage(getString(R.string.empty_fields_message))
                        setTitle(getString(R.string.empty_fields_title))
                    }
                    .create()
        }

        errorDialog.show()
    }

    override fun setBookCreated(book: Book) {
        listener.onBookAdded(book)
    }

    interface OnBookAddedListener {
        fun onBookAdded(book: Book)
    }
}
