package com.ciastek.library.remote.authors.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciastek.library.R

class AuthorDetailsFragment: Fragment() {

    companion object {

        const val AUTHOR_ID = "author_id"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.author_details_fragment, container, false)

    override fun onStart() {
        super.onStart()

        val id = requireArguments().get(AUTHOR_ID)

        Toast.makeText(context,
                       "Opened details for author with id: $id",
                       Toast.LENGTH_SHORT).show()
    }
}
