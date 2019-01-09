package com.diogo.oliveira.mymovies.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

/**
 * Created in 27/07/18 10:22.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
data class Movie(
        @Expose
        @SerializedName("id")
        var id: Int,
        @Expose
        @SerializedName("title")
        var title: String,
        @Expose
        @SerializedName("release_date")
        var releaseDate: String,
        @Expose
        @SerializedName("overview")
        var overView: String,
        @Expose
        @SerializedName("poster_path")
        var cover: String,

        @Expose(serialize = false, deserialize = false)
        var  isFavorite: Boolean = false) : Parcelable
{
    override fun toString(): String
    {
        return title
    }

    private companion object : Parceler<Movie>
    {
        override fun create(parcel: Parcel): Movie
        {
            return Movie(
            parcel.readInt(),
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "")
        }

        override fun Movie.write(parcel: Parcel, flags: Int)
        {
            parcel.writeInt(id)
            parcel.writeString(title)
            parcel.writeString(releaseDate)
            parcel.writeString(overView)
            parcel.writeString(cover)
        }
    }
}
