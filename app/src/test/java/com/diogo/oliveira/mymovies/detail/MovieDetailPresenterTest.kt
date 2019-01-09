package com.diogo.oliveira.mymovies.detail

import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MovieDetailPresenterTest
{
    @Mock
    private lateinit var view: MovieDetailInteractor.View
    private lateinit var presenter: MovieDetailPresenter
    @Mock
    private lateinit var repository: MoviesRepository

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        presenter = MovieDetailPresenter(repository)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun movieAction()
    {
//        val movie = mock(Movie::class.java)
//        `when`(repository.isFavorite(movie)).thenReturn(true)
//        presenter.movieAction()
//
//        verify(repository).isFavorite(movie)
    }
}