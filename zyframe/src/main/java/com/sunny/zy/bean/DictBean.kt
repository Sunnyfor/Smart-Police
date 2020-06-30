package com.sunny.zy.bean


class DictBean {

    var id: Int = 0
    var code: String? = null    //最终匹配的id
    var value: String? = null   //最终需要的值：特警、交警、公安等
    var name: String? = null    //type名称：警种、性别、学历等
    var type: String? = null    //type类型：policeClassification、sex、education.etc
    var orderNum: Int = 0
    var remark: String? = null
    var delFlag: Int = 0
    var gysList: String? = null

    // 提供给地区选择器使用
    override fun toString(): String {
        return name ?: ""
    }

}