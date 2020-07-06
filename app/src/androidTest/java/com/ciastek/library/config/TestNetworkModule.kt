package com.ciastek.library.config

import com.ciastek.library.di.modules.NetworkModule
import com.ciastek.library.remote.books.RemoteBooksService
import retrofit2.Retrofit

class TestNetworkModule: NetworkModule() {

    override fun provideBooksService(retrofit: Retrofit): RemoteBooksService {
        return FakeRemoteBookService()
    }
}