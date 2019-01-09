package com.dao.mobile.artifact.design.list.multichoice

/**
 * Created in 20/08/18 12:32.
 *
 * @author Diogo Oliveira.
 */
interface OnViewHolderCallback
{
    fun onClick(holder: MultiChoice.ViewHolder)

    fun onLongClick(holder: MultiChoice.ViewHolder): Boolean
}