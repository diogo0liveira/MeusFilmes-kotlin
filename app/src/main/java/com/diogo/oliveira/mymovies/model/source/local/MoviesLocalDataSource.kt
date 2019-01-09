package com.diogo.oliveira.mymovies.model.source.local

import com.dao.mobile.artifact.common.data.ResultDatabase
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.local.data.MoviesDataSource
import java.util.*
import javax.inject.Inject

/**
 * Created in 03/08/18 13:14.
 *
 * @author Diogo Oliveira.
 */
class MoviesLocalDataSource @Inject constructor(private val dataSource: MoviesDataSource)
{
    fun save(movie: Movie): ResultDatabase
    {
        return dataSource.insert(movie)
    }

    fun delete(movie: Movie): ResultDatabase
    {
        return dataSource.delete(movie)
    }

    fun isFavorite(movie: Movie): Boolean
    {
        return dataSource.contains(movie)
    }

    fun search(): HashSet<Movie>
    {
        return dataSource.findAll()
    }
}