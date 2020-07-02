package com.zhkj.smartpolice.base

import com.zhkj.smartpolice.login.bean.UserInfoBean
import java.lang.reflect.Field


object UserManager {

    private var userinfo = UserInfoBean()

    /**
     *用户实体类取值
     */
    fun getInfo() = userinfo


    /**
     *用户实体类赋值
     */

    fun setInfo(userInfoBean: UserInfoBean) {

        this.userinfo.roleId = userInfoBean.roleId ?: 0
        this.userinfo.roleName = userInfoBean.roleName ?: ""
        this.userinfo.remark = userInfoBean.remark ?: ""
        this.userinfo.deptId = userInfoBean.deptId ?: 0
        this.userinfo.deptName = userInfoBean.deptName ?: ""
        this.userinfo.menuIdList = userInfoBean.menuIdList ?: ""
        this.userinfo.deptIdList = userInfoBean.menuIdList ?: ""
        this.userinfo.createTime = userInfoBean.createTime ?: ""

    }
}