package com.diogo.oliveira.mymovies.list

import androidx.loader.app.LoaderManager
import com.diogo.oliveira.mymovies.model.source.repository.MoviesRepository
import com.diogo.oliveira.mymovies.util.di.annotations.ActivityScoped
import dagger.Module
import dagger.Provides


/**
 * Created in 15/08/18 17:04.
 *
 * @author Diogo Oliveira.
 */
@Module
/*abstract*/ class MyMoviesModule
{
//    @Binds
//    internal abstract fun provideMyMoviesView(activity: MyMoviesActivity): MyMoviesInteractor.View

//    @Module
//    companion object
//    {
//        @Provides
//        @JvmStatic
//        @ActivityScoped
//        fun provideMyMoviesPresenter(repository: MoviesRepository, activity: MyMoviesActivity): MyMoviesInteractor.Presenter
//        {
//            return MyMoviesPresenter(repository, LoaderManager.getInstance(activity))
//        }
//    }

    @Provides
    @ActivityScoped
    fun provideMyMoviesPresenter(repository: MoviesRepository, activity: MyMoviesActivity): MyMoviesInteractor.Presenter
    {
        return MyMoviesPresenter(repository, LoaderManager.getInstance(activity))
    }
}