package com.dao.mobile.artifact.design.list;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created in 11/08/16 11:36.
 *
 * @author Diogo Oliveira.
 */
public class Restore<T extends Parcelable> implements Parcelable
{
    private T item;
    private int position;

    public Restore(T item, int position)
    {
        this.item = item;
        this.position = position;
    }

    public T getItem()
    {
        return item;
    }

    public void setItem(T item)
    {
        this.item = item;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public void clear()
    {
        this.item = null;
        this.position = -1;
    }

    //region Parcelable

    protected Restore(Parcel in)
    {
        this.item = in.readParcelable(Restore.class.getClassLoader());
        this.position = in.readInt();
    }

    public static final Creator<Restore> CREATOR = new Creator<Restore>()
    {
        @Override
        public Restore createFromParcel(Parcel source) {return new Restore(source);}

        @Override
        public Restore[] newArray(int size) {return new Restore[size];}
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(this.item, flags);
        dest.writeInt(this.position);
    }

    //endregion
}
