package com.diogo.oliveira.mymovies

import androidx.test.runner.AndroidJUnitRunner
import android.app.Application
import android.content.Context


class TestAndroidJUnitRunner : AndroidJUnitRunner()
{
    @Throws(Exception::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application
    {
        return super.newApplication(cl, TestAppController::class.java.name, context)
    }
}