package com.diogo.oliveira.mymovies.util.di

import android.content.Context
import com.diogo.oliveira.mymovies.AppController
import com.diogo.oliveira.mymovies.util.di.network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AppModule
{
    @Provides
    @Singleton
    fun provideContext(application: AppController): Context
    {
        return application.applicationContext
    }
}
