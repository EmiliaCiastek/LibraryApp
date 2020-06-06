package com.ciastek.library.remote.authors.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.ciastek.library.R
import com.ciastek.library.getApiUrl
import com.ciastek.library.remote.authors.di.AuthorsViewModelFactory
import com.ciastek.library.remote.authors.repository.FakeRemoteAuthorsRepository
import com.ciastek.library.remote.authors.repository.RemoteAuthorsService
import com.ciastek.library.remote.books.repository.RemoteBooksService
import com.ciastek.library.showErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_remote_authors.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteAuthorsFragment : Fragment() {

    private val authorsAdapter = AuthorsAdapter()
    private val authorsViewModel: AuthorsViewModel by viewModels {
        AuthorsViewModelFactory(FakeRemoteAuthorsRepository(), AndroidSchedulers.mainThread())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_remote_authors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorsList.apply {
            adapter = authorsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        authorsViewModel.authors.observe(viewLifecycleOwner, Observer {
            authorsAdapter.setAuthors(it)
            if(it.isEmpty()){
                showErrorMessage(context)
            }
        })
    }

    override fun onStart() {
        super.onStart()

        authorsViewModel.fetchAuthors()
    }

    private fun getAuthorsService(): RemoteAuthorsService {
        val retrofit = Retrofit.Builder()
                .baseUrl(getApiUrl(resources))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(RemoteAuthorsService::class.java)
    }
}
