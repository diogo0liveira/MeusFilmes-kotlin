package com.diogo.oliveira.mymovies.model.source.remote

import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.model.source.MovieDataSourceInteractor
import com.diogo.oliveira.mymovies.pojo.SearchResult
import com.diogo.oliveira.mymovies.util.network.ResultError
import com.diogo.oliveira.mymovies.util.network.Status
import com.diogo.oliveira.mymovies.util.network.retrofit.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 03/08/18 13:15.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class MoviesRemoteDataSource @Inject constructor(private val service: Services.Movies) : MovieDataSourceInteractor
{
    override fun search(query: String, listener: MovieDataSourceInteractor.ListMovieListener)
    {
        val call: Call<SearchResult> = service.list(query)

        call.enqueue(object : Callback<SearchResult>
        {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>)
            {
                if(response.isSuccessful)
                {
                    listener.onListSuccess(response.body()!!.results)
                }
                else
                {
                    listener.onListError(ResultError.parse(response))
                }
            }

            override fun onFailure(call: Call<SearchResult>, throwable: Throwable)
            {
                throwable.printStackTrace()
                listener.onListError(ResultError(Status.SERVICE_UNAVAILABLE, R.string.app_internal_server_unavailable))
            }
        })
    }
}