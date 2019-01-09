package com.diogo.oliveira.mymovies.list

import android.content.Context
import com.dao.mobile.artifact.common.mvp.IPresenter
import com.dao.mobile.artifact.common.mvp.IView
import com.diogo.oliveira.mymovies.model.Movie

/**
 * Created in 03/08/18 12:02.
 *
 * @author Diogo Oliveira.
 */
interface MyMoviesInteractor
{
    interface View : IView
    {
        fun context(): Context

        fun loadingMyMoviesList(list: List<Movie>)

        fun startSearchMoviesActivity()
    }

    interface Presenter : IPresenter<View>
    {
        fun loadMyMovieList()
    }
}