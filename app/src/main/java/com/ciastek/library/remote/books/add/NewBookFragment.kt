package com.ciastek.library.remote.books.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.ciastek.library.R
import com.jakewharton.rxbinding3.widget.selectionEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_new_book.*


class NewBookFragment : Fragment() {

    private val disposable = CompositeDisposable()
    private val list = listOf("",
                      "Gregory Maquire",
                      "Tess Gerritsen",
                      "Jennifer Niven",
                      "Harlan Coben")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_new_book, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataAdapter = ArrayAdapter<String>(context,
                                               android.R.layout.simple_spinner_item,
                                               list)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        author_spinner.adapter = dataAdapter

        author_spinner.selectionEvents()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { event -> Log.d("spinner", event.view.selectedItemId.toString()) }
                .apply {
                    disposable.add(this)
                }
    }

    override fun onStop() {
        disposable.clear()

        super.onStop()
    }
}