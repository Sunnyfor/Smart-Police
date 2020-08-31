package com.zhkj.smartpolice.stadium.fragment

import com.sunny.zy.activity.PullRefreshFragment
import com.zhkj.smartpolice.stadium.adapter.StadiumResourceAdapter2

/**
 * Desc
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/8/31 22:49
 */
class StadiumFragment : PullRefreshFragment<Any>() {

    var shopType = 0

    init {
        adapter = StadiumResourceAdapter2()
    }
}