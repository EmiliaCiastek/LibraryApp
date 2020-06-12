package com.ciastek.library.remote.authors.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ciastek.library.R

class AuthorsAdapter(private val authorClicked: (AuthorModel) -> Unit) : RecyclerView.Adapter<AuthorViewHolder>() {

    private val authors = mutableListOf<AuthorModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder =
            AuthorViewHolder(LayoutInflater.from(parent.context)
                                     .inflate(R.layout.author_item_layout, parent, false),
                             authorClicked)

    override fun getItemCount(): Int = authors.size

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.bind(authors[position])
    }

    fun setAuthors(authors: List<AuthorModel>) {
        this.authors.clear()
        this.authors.addAll(authors)
        notifyDataSetChanged()
    }
}
