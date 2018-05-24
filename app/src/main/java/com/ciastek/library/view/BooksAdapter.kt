package com.ciastek.library.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ciastek.library.R
import com.ciastek.library.model.Book

class BooksAdapter : RecyclerView.Adapter<BookViewHolder>() {
    private val books = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_item_layout, parent, false)

        return BookViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    fun addBook(book: Book) {
        books.add(book)
        notifyItemInserted(books.size - 1)
    }
}