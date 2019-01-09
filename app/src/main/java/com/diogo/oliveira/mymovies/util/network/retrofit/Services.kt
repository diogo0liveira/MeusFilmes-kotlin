package com.diogo.oliveira.mymovies.util.network.retrofit

import android.app.SearchManager.QUERY
import com.dao.mobile.artifact.common.network.ContentType
import com.diogo.oliveira.mymovies.util.API
import com.diogo.oliveira.mymovies.pojo.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created in 06/08/18 09:14.
 *
 * @author Diogo Oliveira.
 */
class Services
{
    interface Movies
    {
        @GET(API.URL_SEARCH)
        @Headers(ContentType.APPLICATION_JSON)
        fun list(@Query(QUERY) query: String): Call<SearchResult>
    }
}