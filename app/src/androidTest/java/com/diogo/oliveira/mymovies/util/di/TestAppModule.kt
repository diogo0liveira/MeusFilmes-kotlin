package com.diogo.oliveira.mymovies.util.di

import android.content.Context
import com.diogo.oliveira.mymovies.TestAppController
import com.diogo.oliveira.mymovies.util.di.network.TestNetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [TestNetworkModule::class])
class TestAppModule
{
    @Provides
    @Singleton
    fun provideContext(application: TestAppController): Context
    {
        return application.applicationContext
    }
}
