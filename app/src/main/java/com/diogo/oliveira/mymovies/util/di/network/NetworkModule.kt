package com.diogo.oliveira.mymovies.util.di.network

import android.content.Context
import com.diogo.oliveira.mymovies.BuildConfig
import com.diogo.oliveira.mymovies.util.API
import com.diogo.oliveira.mymovies.util.network.retrofit.Services
import com.diogo.oliveira.mymovies.util.network.retrofit.authority.TokenRequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created in 15/08/18 14:28.
 *
 * @author Diogo Oliveira.
 */
@Module
class NetworkModule
{
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache = Cache(context.cacheDir, 5 * 1024 * 1024)

//    @Provides
//    @Singleton
//    fun provideCacheInterceptor(): Interceptor
//    {
//        return Interceptor { chain ->
//            val response = chain.proceed(chain.request())
//            val cacheControl = CacheControl.Builder().maxAge(1, TimeUnit.MINUTES).build()
//            response.newBuilder().header("Cache-Control", cacheControl.toString()).build()
//        }
//    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
            HttpLoggingInterceptor().apply {
                level = if(BuildConfig.DEBUG) Level.BODY else Level.NONE
            }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(API.URL).build()

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
    fun provideNetworkService(retrofit: Retrofit): Services.Movies
    {
        return retrofit.create(Services.Movies::class.java)
    }
}