package com.sunny.zy.widget

import android.content.Context
import android.util.AttributeSet
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * Desc 下拉刷新
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2019/11/7 23:24
 */
class PullRefreshLayout : SmartRefreshLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        setRefreshHeader(ClassicsHeader(context))
        setEnableAutoLoadMore(true)//开启自动加载功能
    }

    /**
     * 禁止刷新和加载数据
     */
    fun setUnEnableRefreshAndLoad() {
        setEnableRefresh(false)
        setEnableLoadMore(false)
    }
}