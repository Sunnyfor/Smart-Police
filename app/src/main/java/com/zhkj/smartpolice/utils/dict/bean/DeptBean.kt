package com.zhkj.smartpolice.utils.dict.bean

/**
 * Desc 部门列表
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/12/4 15:00
 */
class DeptBean {
    var deptId: String? = null     // 部门id
    var parentId: String? = null   // 父类id
    var name: String? = null       // 指挥中心
    var parentName: String? = null // 烟台市公安市局

    var listLevel: Int = 1                   //【本地字段】列表级别
    var childrenList = ArrayList<DeptBean>() //【本地字段】子类列表

    var open: Boolean = false      // true
    var orderNum: String? = null   // 0
    var treePath: String? = null   // -1/1/
    var delFlag: String? = null    // 2
    var checked: Any? = null
    var list: Any? = null

}