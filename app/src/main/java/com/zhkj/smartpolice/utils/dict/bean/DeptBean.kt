package com.zhkj.smartpolice.utils.dict.bean

/**
 * Desc 部门列表
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/12/4 15:00
 */
class DeptBean {
    var deptId: String? = null     // 1,
    var parentId: String? = null   // -1,
    var name: String? = null       // "烟台市公安市局"
    var parentName: String? = null
    var orderNum: String? = null   // 0,
    var treePath: String? = null   // "-1/1/",
    var delFlag: String? = null    // 2,
    var open: String? = null       // true,
    var list: String? = null
    var checked: String? = null
}