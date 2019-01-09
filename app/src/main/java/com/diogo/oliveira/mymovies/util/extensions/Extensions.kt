package com.diogo.oliveira.mymovies.util.extensions

import android.widget.ImageView
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.util.GlideApp


/**
 * Created in 03/08/18 16:25.
 *
 * @author Diogo Oliveira.
 */
fun ImageView.load(uri: String)
{
    GlideApp.with(context).load(uri).error(R.drawable.vd_movie_24dp).into(this)
}