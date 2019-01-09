package com.diogo.oliveira.mymovies.search

import android.os.Bundle
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.diogo.oliveira.mymovies.util.LOADER.LOADER_LIST_SEARCH
import com.diogo.oliveira.mymovies.adapter.loader.MyMoviesLoader
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.MovieDataSourceInteractor
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import com.diogo.oliveira.mymovies.util.network.ResultError

/**
 * Created in 06/08/18 10:43.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesPresenter constructor(
        private val repository: MoviesRepository,
        private val loaderManager: LoaderManager) :
        SearchMoviesInteractor.Presenter, LoaderManager.LoaderCallbacks<List<Movie>>
{
    private lateinit var view: SearchMoviesInteractor.View

    override fun initialize(view: SearchMoviesInteractor.View)
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
        loaderManager.initLoader(LOADER_LIST_SEARCH, Bundle.EMPTY, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Movie>>
    {
        return MyMoviesLoader(view.context(), repository)
    }

    override fun onLoadFinished(loader: Loader<List<Movie>>, list: List<Movie>)
    {
        view.loadingSearchResult(list)
    }

    override fun onLoaderReset(loader: Loader<List<Movie>>)
    {
        /* Not Implemented */
    }

    override fun searchMovies(query: String)
    {
        repository.search(query, object : MovieDataSourceInteractor.ListMovieListener
        {
            override fun onListSuccess(list: List<Movie>)
            {
                view.loadingSearchResult(list)
            }

            override fun onListError(error: ResultError)
            {
                view.showToast(error.messageRes, Toast.LENGTH_LONG)
            }
        })
    }
}