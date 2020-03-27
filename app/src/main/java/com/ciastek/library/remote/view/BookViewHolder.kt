package com.ciastek.library.remote.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ciastek.library.R
import kotlinx.android.synthetic.main.remote_book_item_layout.view.book_title as title
import kotlinx.android.synthetic.main.remote_book_item_layout.view.book_cover as cover
import kotlinx.android.synthetic.main.remote_book_item_layout.view.book_author as author

class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind (book: BookModel) {
        itemView.title.text = book.title
        itemView.author.text = book.author
        itemView.cover.setBackgroundResource(R.drawable.cover_placeholder)
    }
}
