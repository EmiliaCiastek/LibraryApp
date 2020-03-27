package com.ciastek.library.remote.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciastek.library.R
import com.ciastek.library.remote.di.BooksViewModelFactory
import com.ciastek.library.remote.repository.RemoteBooksRepositoryImpl
import com.ciastek.library.remote.repository.RemoteBooksService
import kotlinx.android.synthetic.main.activity_remote_library.books_list as booksList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties


class RemoteLibraryActivity : AppCompatActivity() {

    companion object {

        fun newIntent(context: Context) = Intent(context, RemoteLibraryActivity::class.java)
    }

    private val booksViewModel: BooksViewModel by viewModels {
        BooksViewModelFactory(RemoteBooksRepositoryImpl(getBooksService()))
    }
    private val booksAdapter = BooksAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_library)

        booksList.adapter = booksAdapter
        booksList.layoutManager = LinearLayoutManager(this)

        booksViewModel.books.observe(this, Observer {
            booksAdapter.setBooks(it)
        })
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
                .build()

        return retrofit.create(RemoteBooksService::class.java)
    }
}
