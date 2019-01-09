package com.diogo.oliveira.mymovies.util.di

import com.diogo.oliveira.mymovies.detail.MovieDetailActivity
import com.diogo.oliveira.mymovies.detail.MovieDetailModule
import com.diogo.oliveira.mymovies.list.MyMoviesActivity
import com.diogo.oliveira.mymovies.list.MyMoviesModule
import com.diogo.oliveira.mymovies.search.SearchMoviesActivity
import com.diogo.oliveira.mymovies.search.SearchMoviesModule
import com.diogo.oliveira.mymovies.util.di.annotations.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created in 15/08/18 15:16.
 *
 * @author Diogo Oliveira.
 */
@Module
abstract class ActivityBindingModule
{
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MyMoviesModule::class])
    abstract fun bindMyMoviesActivity(): MyMoviesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SearchMoviesModule::class])
    abstract fun bindSearchMoviesActivity(): SearchMoviesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MovieDetailModule::class])
    abstract fun bindMovieDetailActivity(): MovieDetailActivity
}