package com.ciastek.library.di.modules

import android.content.Context
import com.ciastek.library.R
import com.ciastek.library.remote.authors.RemoteAuthorsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(context: Context): Retrofit =
    Retrofit.Builder()
            .baseUrl(getApiUrl(context))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun provideAuthorsService(retrofit: Retrofit): RemoteAuthorsService =
            retrofit.create(RemoteAuthorsService::class.java)

    private fun getApiUrl(context: Context): String {
        context.resources.openRawResource(R.raw.config).use { rawConfig ->
            val properties = Properties()
            properties.load(rawConfig)
            return properties.getProperty("api_url")
        }
    }
}
