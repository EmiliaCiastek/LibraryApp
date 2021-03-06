package com.ciastek.library.common.authors

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ciastek.library.R
import kotlinx.android.synthetic.main.author_item_layout.view.*

class AuthorViewHolder(itemView: View,
                       private val itemClicked: (AuthorModel) -> Unit) : RecyclerView.ViewHolder(itemView) {

    fun bind(author: AuthorModel) {
        itemView.authorName.text = itemView.context.getString(R.string.author_data,
                                                              author.lastName,
                                                              author.name)
        itemView.authorPhoto.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_author))
        itemView.setOnClickListener { itemClicked(author) }
    }
}
