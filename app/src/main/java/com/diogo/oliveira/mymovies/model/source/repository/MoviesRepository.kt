package com.diogo.oliveira.mymovies.model.source.repository

import com.dao.mobile.artifact.common.mvp.Repository
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.MovieDataSourceInteractor
import com.diogo.oliveira.mymovies.model.source.local.MoviesLocalDataSource
import com.diogo.oliveira.mymovies.model.source.remote.MoviesRemoteDataSource
import com.diogo.oliveira.mymovies.util.network.ResultError
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 03/08/18 12:30.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class MoviesRepository @Inject constructor(local: MoviesLocalDataSource, remote: MoviesRemoteDataSource):
        Repository<MoviesLocalDataSource, MoviesRemoteDataSource>(local, remote), MovieDataSourceInteractor
{
    fun save(movie: Movie, listener: MovieDataSourceInteractor.MovieListener)
    {
        val result = local.save(movie)

        if(result.isSuccessful())
        {
            listener.onSuccess(movie)
        }
        else
        {
            listener.onError(ResultError())
        }
    }

    fun delete(movie: Movie, listener: MovieDataSourceInteractor.ActionMovieListener)
    {
        val result = local.delete(movie)

        if(result.isSuccessful())
        {
            listener.onActionSuccess()
        }
        else
        {
            listener.onActionError(ResultError())
        }
    }

    override fun search(query: String, listener: MovieDataSourceInteractor.ListMovieListener)
    {
        remote.search(query, object : MovieDataSourceInteractor.ListMovieListener
        {
            override fun onListSuccess(list: List<Movie>)
            {
                listener.onListSuccess(list)
            }

            override fun onListError(error: ResultError)
            {
                listener.onListError(error)
            }
        })
    }

    fun getMovieList(): List<Movie>
    {
        return ArrayList(local.search())
    }

    fun isFavorite(movie: Movie): Boolean
    {
        return local.isFavorite(movie)
    }
}