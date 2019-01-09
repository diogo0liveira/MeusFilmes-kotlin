package com.diogo.oliveira.mymovies.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.mobile.artifact.common.Strings
import com.dao.mobile.artifact.common.network.NetworkManager
import com.dao.mobile.artifact.design.list.simple.Recycler
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.adapter.MyMoviesAdapter
import com.diogo.oliveira.mymovies.databinding.ActivitySearchMoviesBinding
import com.diogo.oliveira.mymovies.databinding.ViewEmptySearchMoviesBinding
import com.diogo.oliveira.mymovies.detail.MovieDetailActivity
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.util.AppControlActivity
import com.diogo.oliveira.mymovies.util.Extras.MOVIE
import com.diogo.oliveira.mymovies.util.REQUEST.REQUEST_DETAIL_MOVIE
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created in 03/08/18 16:00.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesActivity : AppControlActivity(), SearchMoviesInteractor.View, Recycler.Adapter.OnCollectionChangedListener, MyMoviesAdapter.MovieViewOnClickListener
{
    @Inject
    lateinit var presenter: SearchMoviesInteractor.Presenter
    private lateinit var helperEmpty: ViewEmptySearchMoviesBinding
    private lateinit var helper: ActivitySearchMoviesBinding

    private val adapter: MyMoviesAdapter by lazy {
        MyMoviesAdapter(this, mutableListOf(), this)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_search_movies)
        presenter.initialize(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_search_movies, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView

        searchView.setIconifiedByDefault(true)
        searchView.isIconified = false

        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                if(NetworkManager.isConnected() && NetworkManager.available())
                {
                    helperEmpty.visible = true
                    helperEmpty.showProgressBar = true
                    presenter.searchMovies(query!!)
                    searchView.clearFocus()
                }
                else
                {
                    showToast(R.string.app_internal_no_connection, Toast.LENGTH_LONG)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                if(Strings.isEmpty(newText))
                {
                    adapter.clean()
                }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?)
    {
        when(requestCode)
        {
            REQUEST_DETAIL_MOVIE ->
            {
                if(resultCode == Activity.RESULT_OK)
                {
                    setResult(Activity.RESULT_OK)
                }
            }
            else ->
            {
                super.onActivityResult(requestCode, resultCode, intent)
            }
        }
    }

    override fun context(): Context
    {
        return this
    }

    override fun initializeView()
    {
        if(helper.viewState.isInflated)
        {
            helperEmpty.visible = true
        }
        else
        {
            helper.viewState.viewStub!!.visibility = View.VISIBLE
            helperEmpty = DataBindingUtil.findBinding(helper.viewState.root)!!
            helperEmpty.visible = true
        }

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        helper.searchList.addItemDecoration(divider)
        helper.searchList.setHasFixedSize(true)

        adapter.setOnCollectionChangedListener(this)
        helper.searchList.adapter = adapter
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
        helperEmpty.visible = isEmpty
        helperEmpty.showProgressBar = false
    }

    override fun onMovieViewOnClick(movie: Movie)
    {
        startActivityForResult<MovieDetailActivity>(REQUEST_DETAIL_MOVIE, Pair(MOVIE, movie))
    }

    override fun showToast(text: Int, duration: Int)
    {
        when(duration)
        {
            Toast.LENGTH_SHORT -> toast(text)
            Toast.LENGTH_LONG -> longToast(text)
        }
    }

    override fun loadingSearchResult(list: List<Movie>)
    {
        adapter.setDataList(list.toMutableList())
    }
}