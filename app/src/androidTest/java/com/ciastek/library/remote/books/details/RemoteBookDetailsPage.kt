package com.ciastek.library.remote.books.details

import android.view.View
import android.widget.RatingBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.ciastek.library.R
import com.ciastek.library.remote.books.details.reporitory.BookDetails
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RemoteBookDetailsPage {

    fun verifyBookDetailsDisplayed(book: BookDetails) {
        onView(withId(R.id.book_title))
                .check(matches(withText(book.title)))
        onView(withId(R.id.author_name))
                .check(matches(withText(book.author)))
        onView(withId(R.id.book_rate))
                .check(matches(withRate(book.rating)))
    }

    fun verifyDescriptionVisible(description: String) {
        onView(withId(R.id.book_description))
                .check(matches(allOf(
                        isDisplayed(),
                        withText(description)
                )))
    }

    fun verifyEmptyDescriptionPlaceholder() {
        onView(withId(R.id.book_description))
                .check(matches(withText(R.string.book_description_placeholder)))
    }

    private fun withRate(rating: Double) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("with rating: $rating")
        }

        override fun matchesSafely(view: View): Boolean =
                if (view is RatingBar) {
                    view.rating == rating.toFloat()
                } else {
                    false
                }
    }
}