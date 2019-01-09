package com.diogo.oliveira.mymovies.model.source.repository

import com.dao.mobile.artifact.common.data.Action
import com.dao.mobile.artifact.common.data.ResultDatabase
import com.diogo.oliveira.mymovies.model.Movie
import com.diogo.oliveira.mymovies.model.source.MovieDataSourceInteractor
import com.diogo.oliveira.mymovies.model.source.local.MoviesLocalDataSource
import com.diogo.oliveira.mymovies.model.source.remote.MoviesRemoteDataSource
import com.diogo.oliveira.mymovies.util.any
import com.diogo.oliveira.mymovies.util.eq
import com.diogo.oliveira.mymovies.util.mock
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MoviesRepositoryTest
{
    @Mock
    private lateinit var local: MoviesLocalDataSource
    @Mock
    private lateinit var remote: MoviesRemoteDataSource

    private lateinit var repository: MoviesRepository
    private lateinit var movie: Movie

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        repository = MoviesRepository(local, remote)
        movie = mock(Movie::class.java)
    }

    @Test
    fun `save success`()
    {
        val listener: MovieDataSourceInteractor.MovieListener = mock()
        val result = ResultDatabase(Action.INSERT)
        result.forStmInsert(1)

        `when`(local.save(movie)).thenReturn(result)
        repository.save(movie, listener)

        verify(local).save(movie)
        verify(listener).onSuccess(movie)
    }

    @Test
    fun `delete success`()
    {
        val listener: MovieDataSourceInteractor.ActionMovieListener = mock()
        val result = ResultDatabase(Action.DELETE)
        result.forDelete(1)

        `when`(local.delete(movie)).thenReturn(result)
        repository.delete(movie, listener)

        verify(local).delete(movie)
        verify(listener).onActionSuccess()
    }

    @Test
    fun search()
    {
        val listener: MovieDataSourceInteractor.ListMovieListener = mock()

        doAnswer {
            listener.onListSuccess(listOf())
        }.`when`(remote).search(eq(""), any())

        repository.search("", listener)

        verify(remote).search(eq(""), any())
        verify(listener).onListSuccess(listOf())
    }

    @Test
    fun isFavorite()
    {
        `when`(local.isFavorite(movie)).thenReturn(true)
        repository.isFavorite(movie)

        verify(local).isFavorite(movie)
    }

    @Test
    fun getMovieList()
    {
        assertThat(repository.getMovieList(), `is`(listOf()))
    }
}