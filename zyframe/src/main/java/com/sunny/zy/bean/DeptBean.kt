package com.sunny.zy.bean

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/12 17:25
 */
data class DeptBean(
    var checkedId: ArrayList<String>,
    var sysDept: ArrayList<Info>
) {
    data class Info(
        var deptId: String,
        var parentId: String,
        var name: String
    )

}