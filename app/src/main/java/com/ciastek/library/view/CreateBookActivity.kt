package com.ciastek.library.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.ciastek.library.CreateBookContract
import com.ciastek.library.R
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.LibraryDatabase
import com.ciastek.library.presenter.CreateBookPresenter
import kotlinx.android.synthetic.main.activity_create_book.*

class CreateBookActivity : AppCompatActivity(), CreateBookContract.View {
    private lateinit var presenter: CreateBookContract.Presenter
    private val errorDialog: AlertDialog by lazy {
        AlertDialog.Builder(this) //TODO: move to extension function
                .apply {
                    setMessage(getString(R.string.empty_fields_message))
                    setTitle(getString(R.string.empty_fields_title))
                }
                .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_book) //TODO: create custom view for create/details Book

        presenter = CreateBookPresenter(LibraryDatabase.getInstance(this).bookDao()) //TODO: add Dagger
        presenter.attachView(this)

        save_new_book_button.setOnClickListener {
            val book = Book(title = title_create_editText.text.toString(),
                    author = author_create_editText.text.toString(),
                    isbn = isbn_create_editText.text.toString(),
                    isRead = is_read_create.isChecked)

            presenter.saveBookButtonClicked(book)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showError() {
        errorDialog.show()
    }

    override fun setBookCreated(book: Book) {
        setResult(RESULT_OK, Intent().putExtra(CREATED_BOOK, book))
        finish()
    }

    companion object {
        const val CREATED_BOOK = "Created Book"
        fun newInstance(context: Context): Intent =
                Intent(context, CreateBookActivity::class.java)
    }
}
