package com.sunny.zy.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


/**
 * Desc 日期工具类
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020年5月27日 19:11:54
 */
object DateUtil {

    /**
     * 将时间转换为时间戳
     */
    @Throws(ParseException::class)
    fun dateToStamp(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = simpleDateFormat.parse(s) ?: Date()
        val ts = date.time
        res = ts.toString()
        return res
    }

    /**
     * 将时间戳转换为时间
     */
    fun stampToDate(s: Long, isDateTime: Boolean = false): String {
        val res: String
        val simpleDateFormat = if (isDateTime) {
            SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault())
        } else {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        }
        val date = Date(s)
        res = simpleDateFormat.format(date)
        return res
    }

    /**
     * 判断是否满足18岁
     * time : 毫秒级时间戳，用户选中日期的时间戳
     */
    fun isAgeValid(date: String): Boolean {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val inputDate = simpleDateFormat.parse(date)
        val currentDate = Date()
        val diff = abs(currentDate.time - inputDate.time)
        val diffDays = diff / (24 * 60 * 60 * 1000) / 365
        return diffDays > 18
    }
}