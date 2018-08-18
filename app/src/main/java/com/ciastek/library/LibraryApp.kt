package com.ciastek.library

import android.app.Application
import android.content.Context
import com.ciastek.library.di.components.AppComponent
import com.ciastek.library.di.components.DaggerAppComponent
import com.ciastek.library.di.modules.AppModule

class LibraryApp : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getAppComponent() = appComponent

    companion object {
        fun get(context: Context) = context.applicationContext as LibraryApp
    }
}