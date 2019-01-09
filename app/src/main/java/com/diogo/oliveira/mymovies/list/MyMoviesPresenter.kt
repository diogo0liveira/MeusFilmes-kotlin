package com.diogo.oliveira.mymovies.list

import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.diogo.oliveira.mymovies.util.LOADER.LOADER_LIST_MOVIES
import com.diogo.oliveira.mymovies.adapter.loader.MyMoviesLoader
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository

/**
 * Created in 03/08/18 12:03.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesPresenter constructor(
        private val repository: MoviesRepository,
        private val loaderManager: LoaderManager) :
        MyMoviesInteractor.Presenter, LoaderManager.LoaderCallbacks<List<Movie>>
{
    private lateinit var view: MyMoviesInteractor.View

    override fun initialize(view: MyMoviesInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        /* Not Implemented */
    }

    override fun onRestoreInstanceState(bundle: Bundle?, savedState: Boolean)
    {
        loaderManager.initLoader(LOADER_LIST_MOVIES, Bundle.EMPTY, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Movie>>
    {
        return MyMoviesLoader(view.context(), repository)
    }

    override fun onLoadFinished(loader: Loader<List<Movie>>, list: List<Movie>)
    {
        view.loadingMyMoviesList(list)
    }

    override fun onLoaderReset(loader: Loader<List<Movie>>)
    {
        /* Not Implemented */
    }

    override fun loadMyMovieList()
    {
        loaderManager.restartLoader(LOADER_LIST_MOVIES, Bundle.EMPTY, this)
    }
}