package com.zhkj.smartpolice.login.bean


class UserInfoBean {

    var roleId: Int = 0
    var roleName: String? = null
    var remark: String? = null
    var deptId: Int = 0
    var deptName: String? = null
    var menuIdList: String? = null
    var deptIdList: String? = null
    var createTime: String? = null

    override fun toString(): String {
        return "UserInfoBean(roleId=$roleId, roleName=$roleName, remark=$remark, deptId=$deptId, " +
                "deptName=$deptName, menuIdList=$menuIdList, deptIdList=$deptIdList, createTime=$createTime)"
    }


}