package com.diogo.oliveira.mymovies.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.mobile.artifact.design.list.simple.Recycler
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.SplashScreen
import com.diogo.oliveira.mymovies.adapter.MyMoviesAdapter
import com.diogo.oliveira.mymovies.databinding.ActivityMyMoviesBinding
import com.diogo.oliveira.mymovies.databinding.ViewEmptyMyMoviesBinding
import com.diogo.oliveira.mymovies.detail.MovieDetailActivity
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.Order
import com.diogo.oliveira.mymovies.search.SearchMoviesActivity
import com.diogo.oliveira.mymovies.util.Extras.KEY_ORDER
import com.diogo.oliveira.mymovies.util.Extras.MOVIE
import com.diogo.oliveira.mymovies.util.REQUEST.REQUEST_DETAIL_MOVIE
import com.diogo.oliveira.mymovies.util.REQUEST.REQUEST_SEARCH_MOVIES
import org.jetbrains.anko.startActivityForResult
import javax.inject.Inject

/**
 * Created in 27/07/18 09:50.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesActivity : SplashScreen(), MyMoviesInteractor.View, View.OnClickListener, Recycler.Adapter.OnCollectionChangedListener, MyMoviesAdapter.MovieViewOnClickListener
{
    @Inject
    lateinit var presenter: MyMoviesInteractor.Presenter
    private lateinit var helperEmpty: ViewEmptyMyMoviesBinding
    private lateinit var helper: ActivityMyMoviesBinding
    private lateinit var order: Order

    private val adapter: MyMoviesAdapter by lazy {
        MyMoviesAdapter(this, mutableListOf(), this)
    }

    private val preferences by lazy {
        this.getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_my_movies)
        presenter.initialize(this)

        if(savedInstanceState == null)
        {
            order = getOrder()
            presenter.onRestoreInstanceState(Bundle.EMPTY, false)
        }
        else
        {
            order = savedInstanceState.getParcelable(KEY_ORDER)!!
            presenter.onRestoreInstanceState(savedInstanceState, true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelable(KEY_ORDER, order)
        super.onSaveInstanceState(outState)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean
    {
        val order = menu.findItem(R.id.menu_order)

        if(order != null)
        {
            when(this.order)
            {
                Order.TITLE ->
                {
                    order.subMenu.findItem(R.id.menu_order_title).isChecked = true
                }
                Order.DATE ->
                {
                    order.subMenu.findItem(R.id.menu_order_date).isChecked = true
                }
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_my_movies, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.menu_order_title ->
            {
                item.isChecked = true
                sortList(Order.TITLE)
                return true
            }
            R.id.menu_order_date ->
            {
                item.isChecked = true
                sortList(Order.DATE)
                return true
            }
            else ->
            {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        when(requestCode)
        {
            REQUEST_DETAIL_MOVIE, REQUEST_SEARCH_MOVIES ->
            {
                if(resultCode == RESULT_OK)
                {
                    presenter.loadMyMovieList()
                }
            }
            else ->
            {
                super.onActivityResult(requestCode, resultCode, intent)
            }
        }
    }

    override fun initializeView()
    {
        helper.buttonAdd.setOnClickListener(this)

        if(helper.messageEmpty.isInflated)
        {
            helperEmpty.visible = true
        }
        else
        {
            helper.messageEmpty.viewStub!!.visibility = View.VISIBLE
            helperEmpty = DataBindingUtil.findBinding(helper.messageEmpty.root)!!
            helperEmpty.visible = true
        }

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        helper.moviesList.addItemDecoration(divider)
        helper.moviesList.setHasFixedSize(true)

        adapter.setOnCollectionChangedListener(this)
        helper.moviesList.adapter = adapter
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
        helperEmpty.visible = isEmpty
    }

    override fun context(): Context
    {
        return this
    }

    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.button_add ->
            {
                startSearchMoviesActivity()
            }
        }
    }

    override fun loadingMyMoviesList(list: List<Movie>)
    {
        adapter.setDataList(list.toMutableList())
        sortList(order)
    }

    override fun onMovieViewOnClick(movie: Movie)
    {
        startActivityForResult<MovieDetailActivity>(REQUEST_DETAIL_MOVIE, Pair(MOVIE, movie))
    }

    override fun startSearchMoviesActivity()
    {
        startActivityForResult<SearchMoviesActivity>(REQUEST_SEARCH_MOVIES)
    }

    private fun sortList(order: Order)
    {
        this.order = order
        saveOrder(order)

        when(order)
        {
            Order.TITLE ->
            {
                adapter.sort { it -> it.title }
            }
            Order.DATE ->
            {
                adapter.sort(true) { it -> it.releaseDate }
            }
        }
    }

    private fun saveOrder(order: Order)
    {
        preferences.edit().putString(KEY_ORDER, order.name).apply()
    }

    private fun getOrder(): Order
    {
        return Order.valueOf(preferences.getString(KEY_ORDER, Order.TITLE.name)!!)
    }
}