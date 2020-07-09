package com.zhkj.smartpolice.mine.bean


class UserBean {

    val authorities: String? = null
    val avatar: String? = null
    val createTime: String? = null
    val deptId: String? = null
    val deptName: String? = null
    val email: String? = null
    val isDisables: String? = null
    val isRegular: String? = null
    val loginId: String? = null
    val mobile: String? = null
    val nickName: String? = null
    val policeId: String? = null
    val policeNumber: String? = null
    val position: String? = null
    val roleIdList: List<Int>? = null
    val salt: String? = null
    val sex: String? = null
    val sign: String? = null
    val status: String? = null
    val userId: String? = null
    val userName: String? = null

    override fun toString(): String {
        return "UserBean(authorities=$authorities, avatar=$avatar, createTime=$createTime, deptId=$deptId, deptName=$deptName, email=$email, isDisables=$isDisables, isRegular=$isRegular, loginId=$loginId, mobile=$mobile, nickName=$nickName, policeId=$policeId, policeNumber=$policeNumber, position=$position, roleIdList=$roleIdList, salt=$salt, sex=$sex, sign=$sign, status=$status, userId=$userId, userName=$userName)"
    }
}