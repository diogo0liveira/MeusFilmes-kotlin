package com.diogo.oliveira.mymovies.util

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dao.mobile.artifact.common.ApplicationControlActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created in 03/08/18 16:45.
 *
 * @author Diogo Oliveira.
 */
@SuppressLint("Registered")
open class AppControlActivity : ApplicationControlActivity(), HasSupportFragmentInjector
{
    @Inject
    internal lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>
    {
        return fragmentInjector
    }
}