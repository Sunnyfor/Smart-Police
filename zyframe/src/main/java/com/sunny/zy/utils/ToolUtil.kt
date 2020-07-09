package com.sunny.zy.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ApplicationInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


/**
 * =======================================================================================================
 * Desc 系统级工具类
 * =======================================================================================================
 */

/**
 * 剪贴板管理
 */
fun getClipboardManager(context: Context, clipLabel: String, clipContent: String?) {
    try {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(clipLabel, clipContent)
        clipboardManager.setPrimaryClip(clipData)
        ToastUtil.show("复制成功！")
    } catch (e: Exception) {
        ToastUtil.show("复制失败！${e.message}")
    }
}


/**
 * 判断当前应用是否是debug状态
 */
fun isApkInDebug(context: Context): Boolean {
    return try {
        context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: Exception) {
        false
    }
}

/**
 * 获取当前时间2020-04-14 15:05
 */
fun getDateTime(): String {
    return SimpleDateFormat("yyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
}

/**
 * MD5加密
 */
fun md5(text: String): String {
    try {
        val instance: MessageDigest = MessageDigest.getInstance("md5")//获取md5加密对象
        val digest: ByteArray = instance.digest(text.toByteArray())   //对字符串加密，返回字节数组
        val sb = StringBuffer()
        for (b in digest) {
            val i: Int = b.toInt() and 0xff //获取低八位有效值
            var hexString = Integer.toHexString(i)  //将整数转化为16进制
            if (hexString.length < 2) {
                hexString = "0$hexString"//如果是一位的话，补0
            }
            sb.append(hexString)
        }
        return sb.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 手机号校验
 */
fun isPhoneValid(phone: String): Boolean {
    if (phone.isEmpty()) {
        ToastUtil.show("请输入手机号")
        return false
    }
    if (!checkPhoneFormat(phone)) {
        ToastUtil.show("请输入正确的手机号")
        return false
    }
    return true
}

/**
 * 校验手机号格式
 */
fun checkPhoneFormat(phone: String?): Boolean {
    val reg = Regex("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9])|(19[0|9]))\\d{8}$")
    return phone?.matches(reg) ?: false
}

/**
 * 校验邮箱格式
 */
fun checkEmailFormat(email: String?): Boolean {
    val reg = Regex("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
    return email?.matches(reg) ?: false
}

/**
 * 校验图片格式
 */
fun checkImageFormat(imgFormat: String?): Boolean {
    val reg = Regex("(?i).+?\\.(jpg|gif|bmp|png|jpeg)")
    return imgFormat?.matches(reg) ?: false
}

/**
 * 校验企业信用代码
 */
fun checkCreateCode(code: String?): Boolean {
    val reg = Regex("^[A-Za-z0-9]+$")
    return code?.matches(reg) ?: false
}

/**
 * 判断字符串是否为空
 */
fun isStrEmpty(str: String?, defaultStr: String? = ""): String {
    return if (str.isNullOrEmpty()) {
        defaultStr ?: ""
    } else {
        str
    }
}

/**
 * 判断集合是否为空
 */
fun <D> isListEmpty(list: List<D>?): Boolean {
    return list == null || list.isEmpty()
}