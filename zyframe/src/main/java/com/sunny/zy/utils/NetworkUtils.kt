package com.sunny.zy.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * 网络请求判断
 */
object NetworkUtils {

    /**
     * 没有可用网络
     */
    private const val TYPE_UN_ACTIVE = -2

    /**
     * pppoe连接
     */
    const val TYPE_PPPOE = 18

    fun isNetworkAvaliable(con: Context): Boolean {
        val connectivityManager = con
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val net = connectivityManager.activeNetworkInfo
        return net != null && net.isAvailable && net.isConnected
    }

    /**
     * 返回当前可用的网络类型
     *
     * @param con
     * @return
     */
    fun getNetworkType(con: Context): Int {
        val cm = con
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netinfo = cm.activeNetworkInfo
        return if (netinfo != null && netinfo.isAvailable) {
            netinfo.type
        } else TYPE_UN_ACTIVE
    }

}