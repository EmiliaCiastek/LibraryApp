package com.ciastek.library.remote.books.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciastek.library.R
import com.ciastek.library.remote.books.di.BooksViewModelFactory
import com.ciastek.library.remote.books.repository.RemoteBooksRepositoryImpl
import com.ciastek.library.remote.books.repository.RemoteBooksService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_remote_books.books_list as booksList
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties

class RemoteBooksFragment : Fragment() {

    private val booksViewModel: BooksViewModel by viewModels {
        BooksViewModelFactory(RemoteBooksRepositoryImpl(getBooksService(), Schedulers.io()),
                              AndroidSchedulers.mainThread())
    }
    private val booksAdapter = BooksAdapter()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_remote_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        booksList.adapter = booksAdapter
        booksList.layoutManager = LinearLayoutManager(context)

        booksViewModel.books.observe(viewLifecycleOwner, Observer {
            booksAdapter.setBooks(it)
            if(it.isEmpty()) {
                showErrorMessage()
                //TODO: Add swipe up to refresh
            }
        })
    }

    private fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.fetching_error_message), Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()

        booksViewModel.fetchBooks()
    }

    private fun getApiUrl(): String {
        resources.openRawResource(R.raw.config).use { rawConfig ->
            val properties = Properties()
            properties.load(rawConfig)
            return properties.getProperty("api_url")
        }
    }

    private fun getBooksService(): RemoteBooksService {
        val retrofit = Retrofit.Builder()
                .baseUrl(getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(RemoteBooksService::class.java)
    }
}
