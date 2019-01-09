package com.dao.mobile.artifact.common;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * Created on 06/07/16 10:20.
 *
 * @author Diogo Oliveira.
 */
public class Drawables
{
    /**
     * Colore um drawable.
     *
     * @param id    "id do drawable".
     * @param color "id da cor que será utilizada no drawable".
     *
     * @return (drawable na cor pretendida)
     */
    public static Drawable draw(@DrawableRes int id, @ColorRes int color)
    {
        Drawable drawable = DrawableCompat.wrap(get(id));
        DrawableCompat.setTint(drawable, Colors.get(color));
        return drawable;
    }

    /**
     * Colore um drawable.
     *
     * @param drawable "drawable".
     * @param color    "cor que será utilizada no drawable".
     *
     * @return (drawable na cor pretendida)
     */
    public static Drawable draw(Drawable drawable, @ColorRes int color)
    {
        DrawableCompat.setTint(drawable, Colors.get(color));
        return drawable;
    }

    /**
     * Colore um drawable.
     *
     * @param id    "id do drawable".
     * @param color "cor que será utilizada no drawable".
     *
     * @return (drawable na cor pretendida)
     */
    public static Drawable tint(@DrawableRes int id, @ColorInt int color)
    {
        Drawable drawable = DrawableCompat.wrap(get(id));
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    /**
     * Colore um drawable.
     *
     * @param drawable "drawable".
     * @param color    "cor que será utilizada no drawable".
     *
     * @return (drawable na cor pretendida)
     */
    public static Drawable tint(Drawable drawable, @ColorRes int color)
    {
        DrawableCompat.setTint(drawable, Colors.get(color));
        return drawable;
    }

    /**
     * Scala o tamanho de um drawable.
     *
     * @param id   "id do drawable".
     * @param size "tamanho que será utilizado".
     *
     * @return (drawable no tamanho pretendido)
     */
    public static Drawable scale(@DrawableRes int id, int size)
    {
        Drawable drawable = DrawableCompat.wrap(get(id));
        drawable.setBounds(0, 0, size, size);
        return drawable;
    }

    /**
     * Scala o tamanho de um drawable.
     *
     * @param drawable "drawable".
     * @param size     "tamanho que será utilizado".
     *
     * @return (drawable no tamanho pretendido)
     */
    public static Drawable scale(Drawable drawable, int size)
    {
        drawable.setBounds(0, 0, size, size);
        return drawable;
    }

    /**
     * Redimensiona o tamanho de um drawable.
     *
     * @param id     "id do drawable".
     * @param with   largura que será utilizado".
     * @param height altura que será utilizado".
     *
     * @return (drawable no tamanho pretendido)
     */
    public static Drawable resize(@DrawableRes int id, int with, int height)
    {
        Drawable drawable = DrawableCompat.wrap(get(id));
        drawable.setBounds(0, 0, with, height);
        return drawable;
    }

    /**
     * Redimensiona o tamanho de um drawable.
     *
     * @param drawable "drawable".
     * @param with     largura que será utilizado".
     * @param height   altura que será utilizado".
     *
     * @return (drawable no tamanho pretendido)
     */
    public static Drawable resize(Drawable drawable, int with, int height)
    {
        drawable.setBounds(0, 0, with, height);
        return drawable;
    }

    /**
     * Recupera um drawable.
     *
     * @param id "id do drawable".
     *
     * @return (um drawable objeto)
     */
    public static Drawable get(@DrawableRes int id)
    {
        Context context = ApplicationController.getInstance().getContext();
        return ContextCompat.getDrawable(context, id);
    }
}
