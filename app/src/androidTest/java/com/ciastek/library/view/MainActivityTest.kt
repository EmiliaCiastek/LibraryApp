package com.ciastek.library.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ciastek.library.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun shouldDisplayAddButton() {
        onView(withId(R.id.add_fab)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplaySaveButton_WhenAddButtonClicked() {
        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.save_book_fab)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldGoBackToMainActivity_WhenSaveButtonClicked() {
        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.save_book_fab)).perform(click())
        onView(withId(R.id.add_fab)).check(matches(isDisplayed()))
    }
}