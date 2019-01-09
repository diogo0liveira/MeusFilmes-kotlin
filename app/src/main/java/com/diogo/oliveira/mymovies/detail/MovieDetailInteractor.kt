package com.diogo.oliveira.mymovies.detail

import androidx.annotation.StringRes
import com.dao.mobile.artifact.common.annotation.Duration
import com.dao.mobile.artifact.common.mvp.IPresenter
import com.dao.mobile.artifact.common.mvp.IView
import com.diogo.oliveira.mymovies.model.Movie

/**
 * Created in 03/08/18 16:59.
 *
 * @author Diogo Oliveira.
 */
interface MovieDetailInteractor
{
    interface View : IView
    {
        fun putOnForm(movie: Movie)

        fun movieSaveSuccess()

        fun movieDeleteSuccess()

        fun showToast(@StringRes text: Int, @Duration duration: Int)
    }

    interface Presenter : IPresenter<View>
    {
        fun movieAction()
    }
}