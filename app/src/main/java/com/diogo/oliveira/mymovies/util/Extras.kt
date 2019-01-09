package com.diogo.oliveira.mymovies.util

import com.diogo.oliveira.mymovies.BuildConfig

/**
 * Created in 03/08/18 12:16.
 *
 * @author Diogo Oliveira.
 */
object Extras
{
    const val MOVIE = "MOVIE"
    const val KEY_ORDER = "KEY_ORDER"
}

object KeyParameter
{
    const val API_KEY = "api_key"
}

object API
{
    const val URL = "https://api.themoviedb.org"
    private const val URL_IMAGE = "https://image.tmdb.org"
    const val API_KEY = BuildConfig.THEMOVIEDB_API_KEY

    const val URL_SEARCH = ("$URL/3/search/movie")
    const val URL_COVER = ("$URL_IMAGE/t/p/w500")
}

object REQUEST
{
    const val REQUEST_SEARCH_MOVIES = 2000
    const val REQUEST_DETAIL_MOVIE = 2001
}

object LOADER
{
    const val LOADER_LIST_MOVIES = 1000
    const val LOADER_LIST_SEARCH = 1001
}