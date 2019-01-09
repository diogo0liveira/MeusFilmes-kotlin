package com.diogo.oliveira.mymovies.util.di.network

import android.content.Context
import androidx.test.espresso.IdlingRegistry
import com.diogo.oliveira.mymovies.BuildConfig
import com.diogo.oliveira.mymovies.util.API
import com.diogo.oliveira.mymovies.util.network.retrofit.Services
import com.diogo.oliveira.mymovies.util.network.retrofit.authority.TokenRequestInterceptor
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class TestNetworkModule
{
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache = Cache(context.cacheDir, 5 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
            HttpLoggingInterceptor().apply {
                level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, resource: OkHttp3IdlingResource): Retrofit
    {
        IdlingRegistry.getInstance().register(resource)

        return Retrofit.Builder().client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(API.URL).build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient =
            OkHttpClient
                    .Builder()
                    .connectTimeout(10L, TimeUnit.SECONDS)
                    .writeTimeout(10L, TimeUnit.SECONDS)
                    .readTimeout(10L, TimeUnit.SECONDS)
                    .addInterceptor(TokenRequestInterceptor())
                    .addNetworkInterceptor(interceptor)
                    .cache(cache)
                    .build()

    @Provides
    @Singleton
    fun provideOkHttp3IdlingResource(httpClient: OkHttpClient): OkHttp3IdlingResource
    {
        return OkHttp3IdlingResource.create("OkHttp", httpClient)
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): Services.Movies
    {
        return retrofit.create(Services.Movies::class.java)
    }
}
