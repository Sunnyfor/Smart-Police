package com.sunny.zy.bean


class DictBean {

    var id: String? = null
    var name: String? = null    //type名称：性别、警种、学历等
    var type: String? = null    //type类型：sex、education.etc
    var code: String? = null    //最终匹配的id
    var value: String? = null   //最终需要的值：男，女
    var orderNum: Int = 0
    var remark: String? = null
    var delFlag: Int = 0
    var gysList: String? = null

    // 提供给地区选择器使用
    override fun toString(): String {
        return name ?: ""
    }

}