package com.diogo.oliveira.mymovies.model.source

import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.util.network.ResultError

/**
 * Created in 03/08/18 13:17.
 *
 * @author Diogo Oliveira.
 */
interface MovieDataSourceInteractor
{
    fun search(query: String, listener: ListMovieListener)

    interface MovieListener
    {
        fun onSuccess(movie: Movie)

        fun onError(error: ResultError)
    }

    interface ListMovieListener
    {
        fun onListSuccess(list: List<Movie>)

        fun onListError(error: ResultError)
    }

    interface ActionMovieListener
    {
        fun onActionSuccess()

        fun onActionError(error: ResultError)
    }
}