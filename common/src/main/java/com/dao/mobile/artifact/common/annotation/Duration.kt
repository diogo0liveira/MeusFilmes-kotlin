package com.dao.mobile.artifact.common.annotation

import android.widget.Toast
import androidx.annotation.IntDef

/**
 * Created in 20/08/18 14:50.
 *
 * @author Diogo Oliveira.
 */
@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class Duration