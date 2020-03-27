package com.ciastek.library.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ciastek.library.R
import com.ciastek.library.model.Book
import com.forkingcode.espresso.contrib.DescendantViewActions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun shouldDisplaySaveButton_whenAddButtonClicked() {
        onView(withId(R.id.add_book_button)).perform(click())
        onView(withId(R.id.save_new_book_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayBookDetails_whenBookClicked() {
        val sampleBook = sampleBooks[0]

        addNewBook(sampleBook)
        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItem<BookViewHolder>(hasDescendant(withText(sampleBook.title)), click()))
        onView(withText(sampleBook.isbn)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowNewBookOnTheList_whenSaveButtonClicked() {
        val sampleBook = sampleBooks[1]
        addNewBook(sampleBook)

        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.scrollTo<BookViewHolder>(hasDescendant(withText(sampleBook.title)))) //TODO: move to extension function scrollTo(index)
    }

    @Test
    fun shouldShowError_whenSaveButtonClicked_withEmptyBook() {
        onView(withId(R.id.add_book_button)).perform(click())
        onView(withId(R.id.title_create_editText)).perform(click()).perform(typeText(" "), closeSoftKeyboard())
        onView(withId(R.id.save_new_book_button)).perform(click())

        onView(withText(R.string.empty_fields_message)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldRemoveBookFromBooksView() {
        val sampleBook = sampleBooks[3]

        addNewBook(sampleBook)
        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItem<BookViewHolder>(hasDescendant(withText(sampleBook.title)), click()))
        onView(withId(R.id.delete_book)).perform(click())

        onView(withText(sampleBook.title)).check(doesNotExist())
    }

    @Test
    fun shouldUpdateBook_whenSaveButtonClicked() {
        val sampleBook = sampleBooks[4]
        addNewBook(sampleBook)
        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItem<BookViewHolder>(hasDescendant(withText(sampleBook.title)), click()))

        onView(withId(R.id.is_read)).perform(click())
        onView(withId(R.id.save_book_button)).perform(click())
        onView(withId(R.id.books_recycler_view)).perform(RecyclerViewActions.actionOnItem<BookViewHolder>(hasDescendant(withText(sampleBook.title)),
                DescendantViewActions.checkDescendantViewAction(withId(R.id.is_book_read), matches(isChecked()))))
    }

    private fun addNewBook(book: Book) {
        onView(withId(R.id.add_book_button)).perform(click())
        onView(withId(R.id.title_create_editText)).perform(click()).perform(typeText(book.title), closeSoftKeyboard())
        onView(withId(R.id.author_create_editText)).perform(click()).perform(typeText(book.author), closeSoftKeyboard())
        onView(withId(R.id.isbn_create_editText)).perform(click()).perform(typeText(book.isbn), closeSoftKeyboard())

        if (book.isRead) {
            onView(withId(R.id.is_read_create)).perform(click())
        }

        onView(withId(R.id.save_new_book_button)).perform(click())
    }

    private val sampleBooks = listOf(
            Book(title = "Wicked", author = "Gregory Maguire", isbn = "9788392732235", isRead = true),
            Book(title = "Kotlin in action", author = "Dimitry Jemerov", isbn = "9781617293290", isRead = true),
            Book(title = "Effective Java", author = "Joshua Bloch", isbn = "9780134685991"),
            Book(title = "A lion among men", author = "Gregory Maguire", isbn = "9780755348220"),
            Book(title = "Mechaniczny", author = "Ian Tregillis", isbn = "9788379246861")
    )
}