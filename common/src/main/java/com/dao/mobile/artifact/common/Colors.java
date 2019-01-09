package com.dao.mobile.artifact.common;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * Created in 15/08/16 16:06.
 *
 * @author Diogo Oliveira.
 */
public class Colors
{
    public static int get(@ColorRes int color)
    {
        return ContextCompat.getColor(ApplicationController.getInstance().getContext(), color);
    }
}
