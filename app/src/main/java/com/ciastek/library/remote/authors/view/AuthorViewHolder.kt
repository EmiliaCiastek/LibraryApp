package com.ciastek.library.remote.authors.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ciastek.library.R
import kotlinx.android.synthetic.main.author_item_layout.view.*

class AuthorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(author: AuthorModel) {
        itemView.authorName.text = itemView.context.getString(R.string.author_data, author.lastName, author.name)
        itemView.numberOfBooks.text = itemView.context.getString(R.string.number_of_books, author.numberOfBooks)
        itemView.authorPhoto.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_author))
    }
}
