package com.example.spacex.util

import com.example.spacex.R
import com.example.spacex.extension.logW
import com.example.spacex.ui.common.exception.ResponseCodeException
import com.google.gson.JsonSyntaxException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * @ClassName ResponseHandler
 * @Description handle exception information
 * @Author mailo
 * @Date 2021/4/27
 */
object ResponseHandler {

    private const val TAG = "ResponseHandler"

    /**
     * according to exception type
     * @param e exception
     */
    fun getFailureTips(e: Throwable?): String {
        logW(TAG, "getFailureTips exception is ${e?.message}")
        return when (e) {
            is ConnectException -> GlobalUtil.getString(R.string.network_connect_error)
            is SocketTimeoutException -> GlobalUtil.getString(R.string.network_connect_timeout)
            is ResponseCodeException -> GlobalUtil.getString(R.string.network_response_code_error) + e.responseCode
            is NoRouteToHostException -> GlobalUtil.getString(R.string.no_route_to_host)
            is UnknownHostException -> GlobalUtil.getString(R.string.network_error)
            is JsonSyntaxException -> GlobalUtil.getString(R.string.json_data_error)
            else -> {
                GlobalUtil.getString(R.string.unknown_error)
            }
        }
    }
}