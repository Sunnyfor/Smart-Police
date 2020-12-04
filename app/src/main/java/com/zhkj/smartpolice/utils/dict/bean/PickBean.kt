package com.zhkj.smartpolice.utils.dict.bean

import com.sunny.zy.bean.DictBean

/**
 * Desc 选择器通用实体类
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/12/4 19:15
 */
class PickBean {
    var id: String? = null
    var name: String? = null

    constructor()

    constructor(id: String?, name: String?) {
        this.id = id
        this.name = name
    }

    fun init(bean: DictBean) {
        id = bean.code
        name = bean.value
    }

    fun init(bean: DeptBean) {
        id = bean.deptId
        name = bean.name
    }

    override fun toString(): String {
        return name ?: ""
    }

}