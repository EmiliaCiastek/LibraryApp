package com.ciastek.library

import android.app.Application
import android.content.Context
import com.ciastek.library.di.components.AppComponent
import com.ciastek.library.di.components.DaggerAppComponent
import com.ciastek.library.di.modules.AppModule

open class LibraryApp : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = createAppComponent()
    }

    fun getAppComponent() = appComponent

    protected open fun createAppComponent() =
            DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()

    companion object {
        fun get(context: Context) = context.applicationContext as LibraryApp
    }
}