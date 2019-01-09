package com.diogo.oliveira.mymovies.list

import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.diogo.oliveira.mymovies.util.mock
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MyMoviesPresenterTest
{
    @Mock
    private lateinit var view: MyMoviesInteractor.View
    private lateinit var presenter: MyMoviesPresenter

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        presenter = MyMoviesPresenter(mock(MoviesRepository::class.java), mock(LoaderManager::class.java))
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun loadMyMovieList()
    {
        val loader: Loader<List<Movie>> = mock()
        presenter.onLoadFinished(loader, listOf())
        verify(view).loadingMyMoviesList(listOf())
    }
}