package com.ciastek.library.remote.books.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ciastek.library.R

class BooksAdapter(private val bookClicked: (book: BookModel) -> Unit) : RecyclerView.Adapter<BookViewHolder>() {

    private var books = listOf<BookModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
            BookViewHolder(LayoutInflater.from(parent.context)
                                   .inflate(R.layout.remote_book_item_layout, parent, false),
                           bookClicked)

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    fun setBooks(books: List<BookModel>) {
        this.books = books
        notifyDataSetChanged()
    }
}