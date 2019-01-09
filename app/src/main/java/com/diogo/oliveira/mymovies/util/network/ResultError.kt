package com.diogo.oliveira.mymovies.util.network

import androidx.annotation.StringRes
import com.dao.mobile.artifact.common.Resources
import com.diogo.oliveira.mymovies.R
import com.diogo.oliveira.mymovies.util.json.GsonHelper
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created in 03/08/18 13:20.
 *
 * @author Diogo Oliveira.
 */
class ResultError()
{
    private var code: Int = 0
    private var status: Status
    private var message: String
    var messageRes: Int

    init
    {
        this.code = 0
        this.status = Status.INTERNAL_ERROR_CLIENT
        this.messageRes = R.string.app_internal_error_client
        this.message = Resources.string(R.string.app_internal_error_client)
    }

    constructor(status: Status, @StringRes messageRes: Int, message: String = Resources.string(messageRes)) : this()
    {
        this.status = status
        this.messageRes = messageRes
        this.message = message
    }

    companion object
    {
        fun <T> parse(response: Response<T>): ResultError
        {
            val body: ResponseBody = response.errorBody()!!
            return GsonHelper.build().fromJson(body.string(), ResultError::class.java)
        }
    }
}