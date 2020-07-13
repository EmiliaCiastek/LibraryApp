package com.ciastek.library.common

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId

class BottomBarPage {

    fun verifyBottomBarButton(tabId: Int) {
        onView(withId(tabId)).check(matches(isDisplayed()))
    }

    fun verifyTabActive(tabId: Int) {
        onView(withId(tabId)).check(matches(isSelected()))
    }

    fun clickTab(tabId: Int) {
        onView(withId(tabId)).perform(click())
    }
}
