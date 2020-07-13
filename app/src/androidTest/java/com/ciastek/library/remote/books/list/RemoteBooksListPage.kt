package com.ciastek.library.remote.books.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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

    fun openBookDetails(title: String, author: String) {
        val matcher = allOf(withTitle(title), withAuthor(author))

        onView((withId(R.id.books_list))).perform(
                scrollToHolder(matcher),
                actionOnHolderItem(matcher, click()));
    }

    fun verifyBookDetailsVisible() {
        R.id.book_cover.checkViewIsDisplayed()
        R.id.favourite_button.checkViewIsDisplayed()
        R.id.book_cover.checkViewIsDisplayed()
        R.id.book_title.checkViewIsDisplayed()
        R.id.author_name.checkViewIsDisplayed()
    }

    private fun Int.checkViewIsDisplayed() {
        onView(withId(this))
                .check(matches(isDisplayed()))
    }

    private fun withTitle(title: String) = object : TypeSafeMatcher<BookViewHolder>() {
        override fun describeTo(description: Description?) {
            description?.appendText("with title: $title")
        }

        override fun matchesSafely(item: BookViewHolder): Boolean =
                item.itemView.book_title.text == title
    }

    private fun withAuthor(author: String) = object : TypeSafeMatcher<BookViewHolder>() {
        override fun describeTo(description: Description?) {
            description?.appendText("with author: $author")
        }

        override fun matchesSafely(item: BookViewHolder): Boolean =
                item.itemView.book_author.text == author
    }
}
