package com.dao.mobile.artifact.design.list.multichoice

import android.content.Context
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dao.mobile.artifact.design.list.simple.Recycler

/**
 * Created in 20/08/18 12:22.
 *
 * @author Diogo Oliveira.
 */
class MultiChoice
{
    abstract class Adapter<T : Parcelable, V : ViewHolder>
    (context: Context, list: MutableList<T>, private val listener: OnMultiChoiceModeListener? = null) : Recycler.Adapter<T, V>(context, list)

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private var callback: OnViewHolderCallback? = null
        private var listener: View.OnClickListener? = null

        private val onClick = View.OnClickListener {
            callback!!.onClick(this@ViewHolder)
        }

        private val onLongClick = View.OnLongClickListener {
            callback!!.onLongClick(this@ViewHolder)
        }

        init
        {
            view.isClickable = true
            view.isLongClickable = true
            view.setOnClickListener(onClick)
            view.setOnLongClickListener(onLongClick)
        }

        fun setOnViewHolderCallback(listener: OnViewHolderCallback)
        {
            this.callback = listener
        }

        fun setOnClickListener(listener: View.OnClickListener)
        {
            this.listener = listener
        }

        internal fun setActivated(activated: Boolean)
        {
            this.itemView.isActivated = activated
        }

        internal fun performClick(): View.OnClickListener?
        {
            return listener
        }

        internal fun hasPerformClick(): Boolean
        {
            return listener != null
        }
    }
}