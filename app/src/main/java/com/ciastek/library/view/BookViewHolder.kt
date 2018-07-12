package com.ciastek.library.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ciastek.library.model.Book
import kotlinx.android.synthetic.main.book_item_layout.view.*

class BookViewHolder(itemView: View, private inline val listener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            listener(adapterPosition)
        }
    }

    fun bind(book: Book) {
        itemView.is_book_read.isChecked = book.isRead
        itemView.book_author.text = book.author
        itemView.book_title.text = book.title
    }
}