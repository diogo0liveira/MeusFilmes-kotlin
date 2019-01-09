package com.dao.mobile.artifact.design.list.multichoice

import android.os.Parcel
import android.os.Parcelable
import android.util.SparseBooleanArray

/**
 * Created in 20/08/18 13:24.
 *
 * @author Diogo Oliveira.
 */
class MultiChoiceSelector() : Parcelable
{
    private var selecteds = SparseBooleanArray(0)
    private var countSelected = 0

    private fun setSelected(position: Int, selected: Boolean)
    {
        this.countSelected = if(selected) ++countSelected else --countSelected
        this.selecteds.append(position, selected)
    }

    fun isSelected(position: Int): Boolean
    {
        return this.selecteds.get(position)
    }

    fun setStateItem(position: Int): Boolean
    {
        val state = !isSelected(position)
        setSelected(position, state)
        return state
    }

    fun getItensSelected(): List<Int>
    {
        val ids = ArrayList<Int>(selecteds.size())

        for(i in 0 until selecteds.size())
        {
            if(selecteds.valueAt(i))
            {
                ids.add(selecteds.keyAt(i))
            }
        }

        return ids
    }

    fun getItensState(): SparseBooleanArray
    {
        return selecteds
    }

    fun countSelected(): Int
    {
        return countSelected
    }

    fun clear()
    {
        this.selecteds.clear()
        this.countSelected = 0
    }

    constructor(parcel: Parcel) : this()
    {
        selecteds = parcel.readSparseBooleanArray()!!
        countSelected = parcel.readInt()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object
    {
        @JvmField
        val CREATOR: Parcelable.Creator<MultiChoiceSelector> = object : Parcelable.Creator<MultiChoiceSelector>
        {
            override fun createFromParcel(source: Parcel): MultiChoiceSelector = MultiChoiceSelector(source)
            override fun newArray(size: Int): Array<MultiChoiceSelector?> = arrayOfNulls(size)
        }
    }
}