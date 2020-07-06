package com.ciastek.library.remote.books

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.ciastek.library.R
import com.ciastek.library.common.books.BookViewHolder
import kotlinx.android.synthetic.main.book_item_layout.view.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RemoteBooksListPage {

    fun verifyBookVisibleOnList(title: String, author: String) {
        onView(withId(R.id.books_list)).perform(
                scrollToHolder(allOf(
                        withTitle(title),
                        withAuthor(author))))
    }

    private fun withTitle(title: String) = object : TypeSafeMatcher<BookViewHolder>() {
        override fun describeTo(description: Description?) {
        }

        override fun matchesSafely(item: BookViewHolder): Boolean =
                item.itemView.book_title.text == title
    }

    private fun withAuthor(author: String) = object : TypeSafeMatcher<BookViewHolder>() {
        override fun describeTo(description: Description?) {
        }

        override fun matchesSafely(item: BookViewHolder): Boolean =
                item.itemView.book_author.text == author
    }
}