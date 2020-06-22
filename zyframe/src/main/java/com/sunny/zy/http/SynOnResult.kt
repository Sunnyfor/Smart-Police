package com.sunny.zy.http

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class SynOnResult<T>(var serializedName: String? = "data") {
    var typeToken: Type

    init {
        val type = javaClass.genericSuperclass
        val args = (type as ParameterizedType).actualTypeArguments
        typeToken = args[0]
    }
}