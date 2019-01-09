package com.diogo.oliveira.mymovies.search

import androidx.loader.app.LoaderManager
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import com.diogo.oliveira.mymovies.util.di.annotations.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created in 17/08/18 11:16.
 *
 * @author Diogo Oliveira.
 */
@Module
class SearchMoviesModule
{
    @Provides
    @ActivityScoped
    fun provideSearchMoviesPresenter(repository: MoviesRepository, activity: SearchMoviesActivity): SearchMoviesInteractor.Presenter
    {
        return SearchMoviesPresenter(repository, LoaderManager.getInstance(activity))
    }
}