package com.diogo.oliveira.mymovies.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created in 03/08/18 15:00.
 *
 * @author Diogo Oliveira.
 */
enum class Order : Parcelable
{
    TITLE
    {
        override fun value(): Int
        {
            return 0
        }
    },

    DATE
    {
        override fun value(): Int
        {
            return 3
        }
    };

    abstract fun value(): Int

    companion object CREATOR : Parcelable.Creator<Order>
    {
        override fun createFromParcel(parcel: Parcel): Order
        {
            return Order.values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<Order?>
        {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeInt(ordinal)
    }

    override fun describeContents(): Int
    {
        return 0
    }
}