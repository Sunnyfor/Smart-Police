package com.sunny.zy.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.bean.DeptBean
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean

/**
 * Desc 获取部门信息
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/12 17:29
 */
class DeptModel {
    suspend fun loadDept(): DeptBean? {

        val httpResultBean = object :HttpResultBean<BaseModel<DeptBean>>() {}

        ZyHttp.get(
            UrlConstant.DEPARTMENT_OF_TREE,
            null,
            httpResultBean
        )
        if (httpResultBean.isSuccess()) {

            httpResultBean.bean?.let {
                if (it.isSuccess()) {
                    return it.data
                }
            }
        }
        return null
    }
}