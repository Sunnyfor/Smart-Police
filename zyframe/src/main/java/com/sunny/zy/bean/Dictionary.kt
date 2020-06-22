package com.sunny.zy.bean

/**
 * Desc 字典实体类
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/3 18:18
 */
data class Dictionary(
    val code: Int,
    val delFlag: Int,
    val id: Int,
    val name: String,
    val orderNum: Int,
    val remark: Any,
    val type: String,
    val value: String
)