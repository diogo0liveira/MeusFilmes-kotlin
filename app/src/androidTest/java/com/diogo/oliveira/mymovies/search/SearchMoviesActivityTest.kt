package com.diogo.oliveira.mymovies.search

import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.diogo.oliveira.mymovies.R
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchMoviesActivityTest
{
    private val search = "rambo"

    @get:Rule
    val intentRule = IntentsTestRule(SearchMoviesActivity::class.java, false, false)

    @Before
    fun setUp()
    {
        intentRule.launchActivity(null)
    }

    @Test
    fun collectionChanged()
    {
        onView(withText(R.string.empty_list_search_movies)).check(matches(isDisplayed()))

        onView(isAssignableFrom(SearchView.SearchAutoComplete::class.java)).perform(typeText(search), closeSoftKeyboard())
        onView(isAssignableFrom(SearchView.SearchAutoComplete::class.java)).perform(pressImeActionButton())

        onView(withText(R.string.empty_list_search_movies)).check(matches(not(isDisplayed())))
    }
}