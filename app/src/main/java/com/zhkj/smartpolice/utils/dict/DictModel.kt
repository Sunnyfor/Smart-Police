package com.zhkj.smartpolice.utils.dict

import com.sunny.zy.base.BaseModel
import com.sunny.zy.bean.DictBean
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.utils.dict.bean.DeptBean

/**
 * Desc  工具类
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020年10月23日 16:46:27
 */
class DictModel {

    var cacheMap = HashMap<String, ArrayList<DictBean>?>()

    suspend fun getDictBean(code: String, key: String): String {
        return findDiceList(key)?.find { it.code == code }?.value ?: ""
    }

    suspend fun findDiceList(key: String): ArrayList<DictBean>? {
        return if (cacheMap[key] != null) {
            cacheMap[key]
        } else {
            cacheMap[key] = loadDictList(key)
            cacheMap[key]
        }
    }

    private suspend fun loadDictList(type: String): ArrayList<DictBean>? {
        val httpResultBean = object : HttpResultBean<BaseModel<ArrayList<DictBean>>>() {}
        ZyHttp.post(UrlConstant.DICT_LIST_URL, hashMapOf(Pair("type", type)), httpResultBean)
        httpResultBean.isSuccess()
        httpResultBean.bean?.isSuccess()
        return httpResultBean.bean?.data
    }

    suspend fun loadDeptList(): ArrayList<DeptBean>? {
        val httpResultBean = object : HttpResultBean<ArrayList<DeptBean>>() {}
        ZyHttp.get(UrlConstant.DEPT_LIST_URL, hashMapOf(), httpResultBean)
        httpResultBean.isSuccess()
        return httpResultBean.bean
    }

}