package com.ciastek.library.view.config

import com.ciastek.library.LibraryApp
import com.ciastek.library.di.components.AppComponent
import com.ciastek.library.di.components.DaggerAppComponent
import com.ciastek.library.di.modules.AppModule

class TestLibraryApp : LibraryApp() {
    override fun createAppComponent(): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .databaseModule(TestDataBaseModule())
                    .build()
}