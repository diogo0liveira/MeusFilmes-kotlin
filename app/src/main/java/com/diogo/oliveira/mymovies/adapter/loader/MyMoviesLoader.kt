package com.diogo.oliveira.mymovies.adapter.loader

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository

/**
 * Created in 03/08/18 12:23.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesLoader(context: Context, private val repository: MoviesRepository) : AsyncTaskLoader<List<Movie>>(context)
{
    private var list: List<Movie>

    init
    {
        list = ArrayList(0)
    }

    override fun loadInBackground(): List<Movie>
    {
        return repository.getMovieList()
    }

    override fun deliverResult(data: List<Movie>?)
    {
        if(this.isReset)
        {
            return
        }

        if(this.isStarted)
        {
            list = data!!
            super.deliverResult(data)
        }
    }

    override fun onStartLoading()
    {
        if(list.isEmpty() || this.takeContentChanged())
        {
            this.forceLoad()
        }
        else
        {
            this.deliverResult(list)
        }
    }

    override fun onStopLoading()
    {
        this.cancelLoad()
    }

    override fun onReset()
    {
        this.onStopLoading()
    }
}