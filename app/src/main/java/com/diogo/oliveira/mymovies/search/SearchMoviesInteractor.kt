package com.diogo.oliveira.mymovies.search

import android.content.Context
import androidx.annotation.StringRes
import com.diogo.oliveira.mymovies.model.Movie
import com.dao.mobile.artifact.common.annotation.Duration
import com.dao.mobile.artifact.common.mvp.IPresenter
import com.dao.mobile.artifact.common.mvp.IView

/**
 * Created in 06/08/18 10:42.
 *
 * @author Diogo Oliveira.
 */
interface SearchMoviesInteractor
{
    interface View : IView
    {
        fun context(): Context

        fun loadingSearchResult(list: List<Movie>)

        fun showToast(@StringRes text: Int, @Duration duration: Int)
    }

    interface Presenter : IPresenter<View>
    {
        fun searchMovies(query: String)
    }
}