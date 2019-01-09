package com.dao.mobile.artifact.common;

import android.os.Build;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.DimenRes;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

/**
 * Created on 05/10/16 12:29.
 *
 * @author Diogo Oliveira.
 */
public class Resources
{
    public static android.content.res.Resources get()
    {
        /* resource */
        return ApplicationController.getInstance().getResources();
    }

    public static CharSequence text(@StringRes int text)
    {
        /* resource */
        return get().getText(text);
    }

    public static String string(@StringRes int text)
    {
        /* resource */
        return get().getString(text);
    }

    public static String string(@StringRes int text, Object... format)
    {
        /* resource */
        return get().getString(text, format);
    }

    public static String plurals(@PluralsRes int id, int count, Object... format)
    {
        /* resource */
        return get().getQuantityString(id, count, format);
    }

    public static String plurals(@PluralsRes int id, int count)
    {
        /* resource */
        return get().getQuantityString(id, count);
    }

    public static void setTextAppearance(TextView view, @StyleRes int id)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            view.setTextAppearance(id);
        }
        else
        {
            view.setTextAppearance(ApplicationController.getInstance().getContext(), id);
        }
    }

    public static float fontSize(float size)
    {
        /* resource */
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, get().getDisplayMetrics());
    }

    public static float fontSize(@DimenRes int id)
    {
        /* resource */
        return fontSize(get().getDimension(id));
    }
}
