package com.diogo.oliveira.mymovies.util.di

import com.diogo.oliveira.mymovies.TestAppController
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created in 15/08/18 12:20.
 *
 * @author Diogo Oliveira.
 */
@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    TestAppModule::class
])
interface TestAppComponent : AndroidInjector<TestAppController>
{
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TestAppController>()
}