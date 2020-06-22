package com.sunny.zy.leader

import com.sunny.zy.base.IBaseView
import com.sunny.zy.bean.AllUsersBean

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/4 11:13
 */
interface IAllUsersView : IBaseView {

    //查询所有人
    fun getAllUser(list: ArrayList<AllUsersBean>)

}