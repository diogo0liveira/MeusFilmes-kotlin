package com.dao.mobile.artifact.common.mvp

import android.os.Bundle

import com.dao.mobile.artifact.common.Permission

/**
 * Created on 07/10/16 09:09.
 *
 * @author Diogo Oliveira.
 */
interface IPPresenter<T : IView> : IPresenter<T>
{
    fun permissionsRequired()

    fun permission(permission: Permission)

    fun permissionAccepted(requestCode: Int, permissions: Array<String>, grantResults: IntArray)

    override fun onSaveInstanceState(outState: Bundle)

    override fun onRestoreInstanceState(bundle: Bundle?, savedState: Boolean)
}