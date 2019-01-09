package com.diogo.oliveira.mymovies.list

import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.adapter.MyMoviesAdapter
import com.diogo.oliveira.mymovies.search.SearchMoviesActivity
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@LargeTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MyMoviesActivityTest
{
    private val search = "rambo"

    @get:Rule
    val intentRule = IntentsTestRule(MyMoviesActivity::class.java, false, false)

    @Before
    fun setUp()
    {
        intentRule.launchActivity(null)
    }

    @Test
    fun clickButtonAdd()
    {
        onView(withId(R.id.button_add)).perform(click())
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun movieViewOnClick()
    {
        onView(withId(R.id.button_add)).perform(click())
        onView(isAssignableFrom(SearchView.SearchAutoComplete::class.java)).perform(typeText(search), closeSoftKeyboard())
        onView(isAssignableFrom(SearchView.SearchAutoComplete::class.java)).perform(pressImeActionButton())

        onView(withId(R.id.search_list))
                .perform(actionOnItemAtPosition<MyMoviesAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.button_save)).perform(click())
        Espresso.pressBack()
        Espresso.pressBack()

        onView(withId(R.id.movies_list))
                .perform(actionOnItemAtPosition<MyMoviesAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.cover)).check(matches(isDisplayed()))
    }

    @Test
    fun startSearchMoviesActivity()
    {
        onView(withId(R.id.button_add)).perform(click())
        intended(hasComponent(SearchMoviesActivity::class.java.name))
    }
}