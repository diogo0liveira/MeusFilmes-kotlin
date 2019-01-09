package com.diogo.oliveira.mymovies.detail

import android.os.Bundle
import android.widget.Toast
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.util.Extras.MOVIE
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.MovieDataSourceInteractor
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import com.diogo.oliveira.mymovies.util.network.ResultError

/**
 * Created in 03/08/18 16:59.
 *
 * @author Diogo Oliveira.
 */
class MovieDetailPresenter constructor(private val repository: MoviesRepository) : MovieDetailInteractor.Presenter
{
    private lateinit var view: MovieDetailInteractor.View
    private lateinit var movie: Movie

    override fun initialize(view: MovieDetailInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelable(MOVIE, movie)
    }

    override fun onRestoreInstanceState(bundle: Bundle?, savedState: Boolean)
    {
        if(bundle != null)
        {
            movie = bundle.getParcelable(MOVIE)!!

            if(!savedState)
            {
                movie.isFavorite = repository.isFavorite(movie)
            }

            view.putOnForm(movie)
        }
        else
        {
            view.showToast(R.string.movie_detail_not_found, Toast.LENGTH_LONG)
        }
    }

    override fun movieAction()
    {
        if(repository.isFavorite(movie))
        {
            movieNotFavorite()
        }
        else
        {
            movieFavorite()
        }
    }

    private fun movieFavorite()
    {
        repository.save(movie, object : MovieDataSourceInteractor.MovieListener
        {
            override fun onSuccess(movie: Movie)
            {
                view.movieSaveSuccess()
            }

            override fun onError(error: ResultError)
            {
                view.movieDeleteSuccess()
            }

        })
    }

    private fun movieNotFavorite()
    {
        repository.delete(movie, object : MovieDataSourceInteractor.ActionMovieListener
        {
            override fun onActionSuccess()
            {
                view.movieDeleteSuccess()
            }

            override fun onActionError(error: ResultError)
            {
                view.showToast(error.messageRes, Toast.LENGTH_LONG)
            }

        })
    }
}