package com.zhkj.smartpolice.base

import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.mine.bean.UserBean


object UserManager {


    /**
     * 登录用户信息
     */
    private var userinfo = UserInfoBean()

    fun getInfo() = userinfo

    fun setInfo(userInfoBean: UserInfoBean) {

        this.userinfo.roleId = userInfoBean.roleId
        this.userinfo.roleName = userInfoBean.roleName ?: ""
        this.userinfo.remark = userInfoBean.remark ?: ""
        this.userinfo.deptId = userInfoBean.deptId
        this.userinfo.deptName = userInfoBean.deptName ?: ""
        this.userinfo.menuIdList = userInfoBean.menuIdList ?: ""
        this.userinfo.deptIdList = userInfoBean.menuIdList ?: ""
        this.userinfo.createTime = userInfoBean.createTime ?: ""

    }

    /**
     * 个人中心用户信息
     */
    private var userBean = UserBean()

    fun getUserBean() = userBean

    fun setUserBean(userBean: UserBean) {
        this.userBean = userBean
    }

}