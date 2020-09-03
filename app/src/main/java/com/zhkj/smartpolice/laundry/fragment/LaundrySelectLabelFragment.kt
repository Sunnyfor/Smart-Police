package com.zhkj.smartpolice.laundry.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.zhkj.smartpolice.laundry.adapter.LaundryTextLabelAdapter

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/9/3 17:21
 */
class LaundrySelectLabelFragment : PullRefreshFragment<String>() {

    private val allTextList = arrayListOf<String>()

    override fun initView() {
        enableRefresh = false
        enableLoadMore = false
        adapter = LaundryTextLabelAdapter().apply {
            setOnItemClickListener { _, position ->

                val value = if (getData(position).contains(" x "))
                    getData(position).split(" x ")[0] else getData(position)
                allTextList.removeAt(allTextList.lastIndexOf(value))
                count()
            }
        }
        layoutManager = GridLayoutManager(context, 3)
        super.initView()
    }

    fun addData(string: String) {
        if (allTextList.contains(string)) {
            allTextList.add(allTextList.lastIndexOf(string), string)
        } else {
            allTextList.add(string)
        }

        count()

    }

    private fun count() {
        getAllData()?.clear()

        allTextList.groupBy { it }.forEach {
            if (it.value.size > 1)
                getAllData()?.add("${it.key} x ${it.value.size}")
            else
                getAllData()?.add(it.key)

        }
        adapter?.notifyDataSetChanged()
    }

    fun getLabel(): String? {

        val stringBuilder = StringBuilder()

        allTextList.forEach {
            stringBuilder.append(it).append(",")
        }
        if (stringBuilder.isNotEmpty()){
            stringBuilder.deleteCharAt(stringBuilder.lastIndex)
            return stringBuilder.toString()
        }
        return null
    }
}