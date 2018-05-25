package com.ciastek.library.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciastek.library.CreateBookContract
import com.ciastek.library.R
import com.ciastek.library.model.Book
import com.ciastek.library.presenter.CreateBookPresenter
import kotlinx.android.synthetic.main.fragment_create_book.*

class CreateBookFragment : Fragment(), CreateBookContract.View {
    private lateinit var presenter: CreateBookContract.Presenter
    private lateinit var errorDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CreateBookPresenter()
        presenter.attachView(this)

        save_book_fab.setOnClickListener {
            val book = Book(title_editText.text.toString(), author_editText.text.toString())
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
        val data = Intent().putExtra(NEW_BOOK, book)

        activity!!.setResult(RESULT_OK, data)
        activity!!.finish()
    }

    companion object {
        const val NEW_BOOK = "NEW_BOOK"
    }
}
