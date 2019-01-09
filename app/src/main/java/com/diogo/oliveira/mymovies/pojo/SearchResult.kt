package com.diogo.oliveira.mymovies.pojo

import com.diogo.oliveira.mymovies.model.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created in 06/08/18 09:15.
 *
 * @author Diogo Oliveira.
 */
data class SearchResult(
        @Expose
        @SerializedName("page")
        val page: Int,
        @Expose
        @SerializedName("total_results")
        val totalResults: Int,
        @Expose
        @SerializedName("total_pages")
        val totalPages: Int,
        @Expose
        @SerializedName("results")
        val results: List<Movie>)