package com.zhkj.smartpolice.utils

import android.util.Base64

/**
 * Base64转码工具类
 */
object Base64Util {

    /**
     * base64编码
     */
    fun encode(s: String): String {
        return String(Base64.encode(s.toByteArray(), Base64.DEFAULT)).trim()
    }

    /**
     * base64解码
     */
    fun decode(s: String): String {
        return String(Base64.decode(s, Base64.DEFAULT)).trim()
    }
}