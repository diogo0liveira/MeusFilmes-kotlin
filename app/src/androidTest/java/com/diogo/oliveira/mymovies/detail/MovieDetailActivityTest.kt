package com.diogo.oliveira.mymovies.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.util.DataBindingIdlingResourceRule
import com.diogo.oliveira.mymovies.util.Extras
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieDetailActivityTest
{
    private lateinit var movie: Movie

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MovieDetailActivity::class.java, false, false)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    @Before
    fun setUp()
    {
        movie = Movie(
                1,
                "Rambo", "2008-01-24",
                "When governments fail to act on behalf of captive missionaries, ex-Green Beret John James Rambo sets aside his peaceful existence along the Salween River in a war-torn region of Thailand to take action.  Although he's still haunted by violent memories of his time as a U.S. soldier during the Vietnam War, Rambo can hardly turn his back on the aid workers who so desperately need his help.",
                "/mgSMefETH89UnNWMffevxZPKDnO.jpg")

        val intent = Intent().putExtra(Extras.MOVIE, movie)
        activityRule.launchActivity(intent)
    }

    @Test
    fun putOnForm()
    {
        val resource = activityRule.activity.resources
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(movie.title))))
        onView(withId(R.id.overView)).check(matches(withText(movie.overView)))
        onView(withId(R.id.releaseDate)).check(matches(withText(resource.getString(R.string.movie_detail_release_date, movie.releaseDate))))
    }
}