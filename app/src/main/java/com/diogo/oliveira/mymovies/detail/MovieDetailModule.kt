package com.diogo.oliveira.mymovies.detail

import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import com.diogo.oliveira.mymovies.util.di.annotations.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created in 17/08/18 11:36.
 *
 * @author Diogo Oliveira.
 */
@Module
class MovieDetailModule
{
    @Provides
    @ActivityScoped
    fun provideMovieDetailPresenter(repository: MoviesRepository): MovieDetailInteractor.Presenter
    {
        return MovieDetailPresenter(repository)
    }
}