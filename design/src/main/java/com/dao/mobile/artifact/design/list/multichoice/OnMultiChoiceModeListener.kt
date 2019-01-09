package com.dao.mobile.artifact.design.list.multichoice

import androidx.appcompat.view.ActionMode

/**
 * Created in 20/08/18 14:14.
 *
 * @author Diogo Oliveira.
 */
interface OnMultiChoiceModeListener
{
    abstract fun onStartActionMode()

    abstract fun onListChangeState(mode: ActionMode, count: Int)
}