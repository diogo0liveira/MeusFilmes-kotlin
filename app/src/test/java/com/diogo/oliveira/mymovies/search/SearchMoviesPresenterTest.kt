package com.diogo.oliveira.mymovies.search

import androidx.loader.app.LoaderManager
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import com.diogo.oliveira.mymovies.util.any
import com.diogo.oliveira.mymovies.util.eq
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchMoviesPresenterTest
{
    @Mock
    private lateinit var view: SearchMoviesInteractor.View
    private lateinit var presenter: SearchMoviesPresenter
    @Mock
    private lateinit var repository: MoviesRepository

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        presenter = SearchMoviesPresenter(repository, mock(LoaderManager::class.java))
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun searchMovies()
    {
        presenter.searchMovies("")
        verify(repository).search(eq(""), any())
    }
}