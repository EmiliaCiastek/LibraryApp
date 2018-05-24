package com.ciastek.library.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ciastek.library.R

import kotlinx.android.synthetic.main.activity_create_book.*

class CreateBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_book)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreateBookActivity::class.java)
        }
    }

}
