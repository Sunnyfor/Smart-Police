package com.sunny.zy.bean

/**
 * Desc
 * Author 全部用户列表
 * Mail zhangye98@foxmail.com
 * Date 2020/6/4 11:12
 */
class AllUsersBean(
    var userId: String? = null,
    var username: String? = null,
    var salt: String? = null,
    var email: String? = null,
    var mobile: String? = null,
    var status: String? = null,
    var roleIdList: Any? = null,
    var createTime: String? = null,
    var deptId: String? = null,
    var deptName: String? = null,
    var imagesId: Any? = null,
    var sign: Any? = null,
    //本地字段，用于判断是否 已经是参与人
    var isAlreadyJoinPeople: Boolean = false
)