package com.zhkj.smartpolice.mine.bean


class UserBean {

    var userId: String? = null
    var loginId: String? = null
    var nickName: String? = null
    var userName: String? = null
    var email: String? = null
    var mobile: String? = null
    var isDisables: String? = null
    var roleIdList: ArrayList<Int>? = null
    var createTime: String? = null
    var deptId: String? = null
    var deptName: String? = null
    var policeId: String? = null
    var avatar: String? = null
    var sign: String? = null
    var status: String? = null
    var isRegular: String? = null
    var sex: String? = null
    var position: String? = null
    var policeNumber: String? = null
    var userType: String? = null
    var validityTime: String? = null
    var phoneUuid: String? = null
    var leaderId: String? = null
    var verificationCode: String? = null
    var authorities: String? = null

    override fun toString(): String {
        return "UserBean(userId=$userId, loginId=$loginId, nickName=$nickName, userName=$userName, email=$email, mobile=$mobile, isDisables=$isDisables, roleIdList=$roleIdList, createTime=$createTime, deptId=$deptId, deptName=$deptName, policeId=$policeId, avatar=$avatar, sign=$sign, status=$status, isRegular=$isRegular, sex=$sex, position=$position, policeNumber=$policeNumber, userType=$userType, validityTime=$validityTime, phoneUuid=$phoneUuid, leaderId=$leaderId, verificationCode=$verificationCode, authorities=$authorities)"
    }
}