package com.diogo.oliveira.mymovies.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.dao.mobile.artifact.common.DateTime
import com.dao.mobile.artifact.common.Strings
import com.diogo.oliveira.mymovies.util.API
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.util.extensions.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Date

/**
 * Created in 03/08/18 16:17.
 *
 * @author Diogo Oliveira.
 */
@BindingAdapter("visible")
fun visible(view: View, visible: Boolean)
{
    view.visibility = if(visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["text", "date"], requireAll = false)
fun date(view: TextView, text: String?, date: String?)
{
    var message = date ?: DateTime.dateFormatMedium(Date.valueOf(date).time)

    if(!Strings.isEmpty(text))
    {
        message = text?.let { String.format(it, message) }
    }

    view.text = message
}

@BindingAdapter("cover")
fun cover(view: ImageView, uri: String?)
{
    uri?.let { view.load(API.URL_COVER + uri) } ?: run {
        view.setImageResource(R.drawable.vd_movie_120dp)
    }
}

@BindingAdapter("favorite")
fun favorite(view: FloatingActionButton, favorite: Boolean)
{
    view.setImageResource(if(favorite) R.drawable.vd_favorite_24dp else R.drawable.vd_not_favorite_24dp)
}

@BindingAdapter("asyncText", "android:textSize", requireAll = false)
fun asyncText(view: TextView, text: CharSequence, textSize: Int?)
{
    if(textSize != null)
    {
        view.textSize = textSize.toFloat()
    }

    val params = TextViewCompat.getTextMetricsParams(view)
    (view as AppCompatTextView).setTextFuture(PrecomputedTextCompat.getTextFuture(text, params, null))
}