package com.diogo.oliveira.mymovies.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dao.mobile.artifact.design.list.simple.Recycler

import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.databinding.ViewRowMovieBinding
import com.diogo.oliveira.mymovies.model.Movie

/**
 * Created in 03/08/18 15:27.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesAdapter(context: Context, list: MutableList<Movie>, private val listener: MovieViewOnClickListener) :
        Recycler.Adapter<Movie, MyMoviesAdapter.ViewHolder>(context, list)
{
    init
    {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
         val binding: ViewRowMovieBinding = this.inflate(parent, R.layout.view_row_movie)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Movie)
    {
        holder.binding.movie = item
    }

    override fun getItemId(position: Int): Long
    {
        return getItem(position).id.toLong()
    }

    class ViewHolder(val binding: ViewRowMovieBinding, private val listener: MovieViewOnClickListener) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener
    {
        init
        {
            this.itemView.setOnClickListener(this)
        }

        override fun onClick(view: View)
        {
            listener.onMovieViewOnClick(binding.movie!!)
        }

    }

    interface MovieViewOnClickListener
    {
        fun onMovieViewOnClick(movie: Movie)
    }
}