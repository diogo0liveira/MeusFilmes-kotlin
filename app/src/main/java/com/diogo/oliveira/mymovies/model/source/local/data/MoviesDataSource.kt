@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.diogo.oliveira.mymovies.model.source.local.data

import android.content.ContentValues
import com.dao.mobile.artifact.common.data.BindValue
import com.dao.mobile.artifact.common.data.query.Clause
import com.dao.mobile.artifact.common.data.query.QueryCursor
import com.diogo.oliveira.mymovies.model.Movie
import javax.inject.Inject

/**
 * Created in 03/08/18 13:45.
 *
 * @author Diogo Oliveira.
 */
class MoviesDataSource @Inject constructor(): DBHelper<Movie>(TABLE_MOVIE)
{
    override fun contentValues(movie: Movie, insert: Boolean): ContentValues
    {
        return ContentValues().apply {
            put(COLUMN_MOV_ID, movie.id)
            put(COLUMN_MOV_TITLE, movie.title)
            put(COLUMN_MOV_RELEASE_DATE, movie.releaseDate)
            put(COLUMN_MOV_OVERVIEW, movie.overView)
            put(COLUMN_MOV_COVER, movie.cover)
        }
    }

    override fun bindValue(bindValue: BindValue, movie: Movie): BindValue
    {
       return bindValue.apply {
            set(movie.id)
            set(movie.title)
            set(movie.releaseDate)
            set(movie.overView)
            set(movie.cover)
        }
    }

    override fun model(cursor: QueryCursor): Movie
    {
        return Movie(
                cursor.getInt(COLUMN_MOV_ID),
                cursor.getString(COLUMN_MOV_TITLE),
                cursor.getString(COLUMN_MOV_RELEASE_DATE),
                cursor.getString(COLUMN_MOV_OVERVIEW),
                cursor.getString(COLUMN_MOV_COVER))
    }

    override fun constraints(movie: Movie): Clause
    {
        return Clause()
                .equal(COLUMN_MOV_ID to movie.id)
    }
}