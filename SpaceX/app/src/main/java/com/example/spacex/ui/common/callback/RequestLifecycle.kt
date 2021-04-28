package com.example.spacex.ui.common.callback


/**
 * @ClassName RequestLifecycle
 * @Description requestLifeCycle
 * @Author mailo
 * @Date 2021/4/27
 */
interface RequestLifecycle {
    fun startLoading()

    fun loadFinished()

    fun loadFailed(msg: String?)


}