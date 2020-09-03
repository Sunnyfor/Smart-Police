package com.zhkj.smartpolice.haircut.bean

import java.util.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/13 18:09
 */
data class WeekDayBean(
    var week: String = "",
    var day: Int = 0
) {
    var date: Date? = null
}