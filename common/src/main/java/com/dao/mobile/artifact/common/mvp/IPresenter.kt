package com.dao.mobile.artifact.common.mvp

import android.os.Bundle

/**
 * Created in 28/07/16 12:15.
 *
 * @author Diogo Oliveira.
 */

interface IPresenter<T : IView>
{
    fun initialize(view: T)

    fun onSaveInstanceState(outState: Bundle)

    fun onRestoreInstanceState(bundle: Bundle?, savedState: Boolean)
}
