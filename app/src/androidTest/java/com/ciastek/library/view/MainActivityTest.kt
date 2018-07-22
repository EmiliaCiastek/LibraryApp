package com.ciastek.library.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ciastek.library.R
import com.forkingcode.espresso.contrib.DescendantViewActions
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun shouldDisplaySaveButton_WhenAddButtonClicked() {
        onView(withId(R.id.add_book_button)).perform(click())
        onView(withId(R.id.save_book_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayBookDetails_whenBookClicked() {
        val randomSuffix = Random().nextInt()
        val fakeValue = "something$randomSuffix"

        addNewBook(randomSuffix, fakeValue)
        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItem<BookViewHolder>(hasDescendant(withText(fakeValue)), click()))
        onView(withText(randomSuffix.toString())).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowNewBookOnTheList_WhenSaveButtonClicked() {
        val randomSuffix = Random().nextInt()
        val fakeValue = "something$randomSuffix"

        onView(withId(R.id.add_book_button)).perform(click())
        onView(withId(R.id.author_editText)).perform(click()).perform(typeText(fakeValue), closeSoftKeyboard())
        onView(withId(R.id.title_editText)).perform(click()).perform(typeText(fakeValue), closeSoftKeyboard())
        onView(withId(R.id.save_book_button)).perform(click())

        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.scrollTo<BookViewHolder>(hasDescendant(withText(fakeValue)))) //TODO: move to extension function scrollTo(index)
    }

    @Test
    fun shouldShowError_WhenSaveButtonClicked_withEmptyBook() {
        val randomSuffix = Random().nextInt()
        val fakeValue = "something$randomSuffix"
        onView(withId(R.id.add_book_button)).perform(click())
        onView(withId(R.id.author_editText)).perform(click()).perform(typeText(fakeValue), closeSoftKeyboard())
        onView(withId(R.id.save_book_button)).perform(click())

        onView(withText(R.string.empty_fields_message)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore //TODO: fix ignored tests
    fun shouldRemoveBookFromBooksView() {
        val randomSuffix = Random().nextInt()
        val fakeValue = "something$randomSuffix"
        addNewBook(randomSuffix, fakeValue)
        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItem<BookViewHolder>(hasDescendant(withText(fakeValue)), click()))

        onView(withId(R.id.delete_book)).perform(click())

        onView(withText(fakeValue)).check(doesNotExist())
    }

    @Test
    @Ignore
    fun shouldUpdateBook_whenSaveButtonClicked() {
        val taskPosition = 0
        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<BookViewHolder>(taskPosition, click()))
        val changed = "changed"
        onView(withId(R.id.title_editText)).perform(click()).perform(typeText(changed), closeSoftKeyboard())
        onView(withId(R.id.save_book_button)).perform(click())

        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItem<BookViewHolder>(withText(changed),
                DescendantViewActions.checkViewAction(matches(isDisplayed()))))

    }

    private fun addNewBook(isbn: Int, fakeValue: String) {
        onView(withId(R.id.add_book_button)).perform(click())
        onView(withId(R.id.title_editText)).perform(click()).perform(typeText(fakeValue), closeSoftKeyboard())
        onView(withId(R.id.author_editText)).perform(click()).perform(typeText(fakeValue), closeSoftKeyboard())
        onView(withId(R.id.isbn_editText)).perform(click()).perform(typeText(isbn.toString()), closeSoftKeyboard())
        onView(withId(R.id.save_book_button)).perform(click())
    }
}