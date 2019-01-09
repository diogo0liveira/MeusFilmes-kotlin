package com.dao.mobile.artifact.design.list.simple

import android.content.Context
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dao.mobile.artifact.common.Numbers
import com.google.common.base.Predicate
import com.google.common.collect.Collections2
import java.util.*
import java.util.regex.Pattern

/**
 * Created in 20/08/18 14:47.
 *
 * @author Diogo Oliveira.
 */
class Recycler
{
    abstract class Adapter<T : Parcelable, V : RecyclerView.ViewHolder>
    constructor(private val context: Context, private var list: MutableList<T>) : RecyclerView.Adapter<V>()
    {
        private var changedListener: OnCollectionChangedListener? = null
        private val restore: SparseArray<T> = SparseArray(0)
        private var collection: MutableList<T> = arrayListOf()

        protected fun getItem(position: Int): T = list[position]

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: V, position: Int)
        {
            onBindViewHolder(holder, getItem(position))
        }

        abstract fun onBindViewHolder(holder: V, item: T)

        protected fun <H : ViewDataBinding> inflate(parent: ViewGroup, @LayoutRes layout: Int): H
        {
            return DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false)
        }

        fun itemInsert(item: T): Boolean
        {
            if(list.add(item))
            {
                notifyItemInserted(list.indexOf(item))
                return true
            }

            return false
        }

        fun itemChange(item: T, position: Int): Boolean
        {
            try
            {
                list[position] = item
                notifyItemChanged(position)
                return true
            }
            catch(e: Exception)
            {
                e.printStackTrace()
            }

            return false
        }

        fun itemRemove(item: T?, position: Int): Boolean
        {
            if(item != null && list.remove(item))
            {
                restore.put(position, item)
                notifyItemRemoved(position)
                return true
            }

            return false
        }

        fun itemRemove(list: SparseArray<T>?): Boolean
        {
            if(list != null && list.size() > 0)
            {
                var startPosition = Integer.MAX_VALUE
                var position: Int

                for(i in 0 until list.size())
                {
                    if(this.list.remove(list.valueAt(i)))
                    {
                        position = list.keyAt(i)
                        restore.put(position, list.valueAt(i))
                        startPosition = if(startPosition > position) position else startPosition
                    }
                }

                if(!Numbers.isNegative(startPosition))
                {
                    notifyItemRangeRemoved(startPosition, list.size())
                    return true
                }
            }

            return false
        }

        fun itemRestored(item: T, position: Int): Boolean
        {
            try
            {
                list.add(position, item)
                notifyItemInserted(position)
                restore.clear()
                return true
            }
            catch(e: Exception)
            {
                e.printStackTrace()
            }

            return false
        }

        fun getDataList(): List<T>
        {
            return list
        }

        fun setDataList(list: MutableList<T>)
        {
            this.list = list
            notifyDataSetChanged()
        }

        fun restoreDataList()
        {
            setDataList(collection)
            collection.clear()
        }

        fun restoreItens(): SparseArray<T>
        {
            return restore
        }

        fun sort(comparator: Comparator<T>)
        {
            Collections.sort(list, comparator)
            this.notifyDataSetChanged()
        }

        fun <R : Comparable<R>> sort(desc: Boolean = false, selector: (T) -> R)
        {
            if(desc)
            {
                setDataList(getDataList().sortedByDescending(selector).toMutableList())
            }
            else
            {
                setDataList(getDataList().sortedBy(selector).toMutableList())
            }
        }

        fun clean()
        {
            list = arrayListOf()
            this.notifyDataSetChanged()
        }

        fun setOnCollectionChangedListener(listener: OnCollectionChangedListener)
        {
            changedListener = listener
            registerAdapterDataObserver(AdapterDataObserver())
        }

        protected fun filter(pattern: Pattern)
        {
            collection = ArrayList(list)
            val predicate: Predicate<T> = Predicate { input -> pattern.matcher(input.toString()).find() }
            val collection = Collections2.filter(getDataList(), predicate)
            setDataList(ArrayList(collection))
        }

        internal inner class AdapterDataObserver : RecyclerView.AdapterDataObserver()
        {
            override fun onChanged()
            {
                super.onChanged()
                collectionChanged()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int)
            {
                super.onItemRangeInserted(positionStart, itemCount)
                collectionChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int)
            {
                super.onItemRangeChanged(positionStart, itemCount)
                collectionChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?)
            {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                collectionChanged()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int)
            {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                collectionChanged()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int)
            {
                super.onItemRangeRemoved(positionStart, itemCount)
                collectionChanged()
            }

            private fun collectionChanged()
            {
                changedListener?.onCollectionChanged((itemCount == 0))
            }
        }

        interface OnCollectionChangedListener
        {
            fun onCollectionChanged(isEmpty: Boolean)
        }
    }
}