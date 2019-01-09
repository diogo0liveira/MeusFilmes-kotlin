package com.diogo.oliveira.mymovies

import android.app.Activity
import com.dao.mobile.artifact.common.ApplicationController
import com.diogo.oliveira.mymovies.util.di.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestAppController : ApplicationController(), HasActivityInjector
{
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate()
    {
        super.onCreate()
        DaggerTestAppComponent.builder().create(this).inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}