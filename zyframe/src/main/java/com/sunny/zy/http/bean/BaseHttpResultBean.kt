package com.sunny.zy.http.bean

import com.alibaba.android.arouter.launcher.ARouter
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.ToastUtil
import okhttp3.WebSocketListener
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseHttpResultBean<T>: WebSocketListener() {
    var typeToken: Type

    init {
        val type = javaClass.genericSuperclass
        val args = (type as ParameterizedType).actualTypeArguments
        typeToken = args[0]
    }

    var url = "" //请求URL
    var httpCode = 0 //请求code
    var contentLength = 0L //数据长度
    var readLength = 0L  //当前读取长度
    var done = false //是否完成
    var exception: Exception? = null //网络请求异常信息


    fun httpIsSuccess(): Boolean {
        if (httpCode in 200..299) {
            return true
        }
        return false
    }

    abstract fun notifyData(baseHttpResultBean: BaseHttpResultBean<T>)

    override fun toString(): String {
        return "BaseHttpResultBean(typeToken=$typeToken, httpCode=$httpCode, contentLength=$contentLength, readLength=$readLength, done=$done, exception=$exception)"
    }
}