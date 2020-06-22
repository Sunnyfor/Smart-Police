package com.sunny.zy.http

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/2 16:08
 */
abstract class OnResult<T>(var serializedName: String? = "data") {
    var typeToken: Type

    init {
        val type = javaClass.genericSuperclass
        val args = (type as ParameterizedType).actualTypeArguments
        typeToken = args[0]
    }


    open fun onComplete() {}
    open fun onSuccess(data: T) {}
    open fun onFailed(code: String, message: String) {}
}