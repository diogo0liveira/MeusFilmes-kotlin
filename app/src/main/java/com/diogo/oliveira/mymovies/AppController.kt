package com.diogo.oliveira.mymovies

import android.app.Activity
import com.dao.mobile.artifact.common.ApplicationController
import com.diogo.oliveira.mymovies.util.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService
import javax.inject.Inject

/**
 * Created in 27/07/18 09:48.
 *
 * @author Diogo Oliveira.
 */
class AppController : ApplicationController(), HasActivityInjector
{
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate()
    {
        super.onCreate()
        DaggerAppComponent.builder().create(this).inject(this)
        BuildConfig.DEBUG.let { SQLiteStudioService.instance().start(this) }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}
