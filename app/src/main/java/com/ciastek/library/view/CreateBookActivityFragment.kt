package com.ciastek.library.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciastek.library.R
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.fragment_create_book.*

class CreateBookActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save_book_fab.setOnClickListener {
            val book = Book(title_editText.text.toString(), author_editText.text.toString())
            book.isRead = is_read.isChecked

            val data = Intent().putExtra(NEW_BOOK, book)

            activity!!.setResult(RESULT_OK, data)
            activity!!.finish()
        }
    }

    companion object {
        const val NEW_BOOK = "NEW_BOOK"
    }
}
