package com.diogo.oliveira.mymovies.detail

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.databinding.ActivityMovieDetailBinding
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.util.AppControlActivity
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created in 03/08/18 15:59.
 *
 * @author Diogo Oliveira.
 */
class MovieDetailActivity : AppControlActivity(), MovieDetailInteractor.View, View.OnClickListener
{
    @Inject
    lateinit var presenter: MovieDetailInteractor.Presenter

    private lateinit var helper: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        presenter.initialize(this)

        if(savedInstanceState != null)
        {
            presenter.onRestoreInstanceState(savedInstanceState, true)
        }
        else
        {
            presenter.onRestoreInstanceState(this.intent.extras, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        presenter.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId)
        {
            android.R.id.home ->
            {
                finish()
                true
            }
            else ->
            {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.button_save ->
            {
                presenter.movieAction()
            }
        }
    }

    override fun initializeView()
    {
        setSupportActionBar(helper.toolbar)
        val actionBar = this.supportActionBar

        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        helper.buttonSave.setOnClickListener(this)
    }

    override fun putOnForm(movie: Movie)
    {
        helper.movie = movie
    }

    override fun movieSaveSuccess()
    {
        helper.buttonSave.setImageResource(R.drawable.vd_favorite_24dp)
        setResult(Activity.RESULT_OK)
    }

    override fun movieDeleteSuccess()
    {
        helper.buttonSave.setImageResource(R.drawable.vd_not_favorite_24dp)
        setResult(Activity.RESULT_OK)
    }

    override fun showToast(text: Int, duration: Int)
    {
        when(duration)
        {
            Toast.LENGTH_SHORT -> toast(text)
            Toast.LENGTH_LONG -> longToast(text)
        }
    }
}