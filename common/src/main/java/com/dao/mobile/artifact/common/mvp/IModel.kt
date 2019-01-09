package com.dao.mobile.artifact.common.mvp

import android.content.Context

/**
 * Created in 09/08/16 16:43.
 *
 * @author Diogo Oliveira.
 */
interface IModel<T : IPresenter<*>>
{
    fun initialize(context: Context, presenter: T)
}
