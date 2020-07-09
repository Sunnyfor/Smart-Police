package com.zhkj.smartpolice.app

import com.sunny.zy.http.UrlConstant

object UrlConstant {

    /**
     * 图片下载地址
     */
    const val IMAGE_PATH_URL = "${UrlConstant.host}/sys/attachment/download?attID="

    /**
     * 餐厅模块：订餐记录
     */
    const val MEAL_RECORD_URL = "logistics/shoporders/list"

    /**
     * 餐厅模块：餐厅列表
     */
    const val RESTAURANT_LIST_URL = "logistics/shop/list"

    /**
     * 个人中心：用户信息
     */
    const val USER_INFO_URL = "/sys/user/info"

    /**
     * 个人中心：预约记录
     */
    const val RESERVE_RECORD_URL = "logistics/reserverecord/list"

    /**
     * 个人中心：维修记录
     */
    const val REPAIR_RECORD_URL = "logistics/apply/listMyApply"

    /**
     * 用户登录
     */
    const val USER_LOGIN = "sys/login"

    /**
     * 修改密码
     */
    const val USER_ALTER_PASSWORD = "sys/user/password"

    /**
     * 维修功能：维修部件分类
     */
    const val MAINTAIN_CLASSIFY = "logistics/shop/list"

    /**
     * 维修功能：维修部件列表
     */
    const val MAINTAIN_liST = "logistics/shopgoodslabel/listLabelGoods"

    /**
     * 附件下载
     */
    const val ACCESSORY = "${UrlConstant.host}/sys/attachment/download"

    /**
     * 维修功能：申请维修提交
     */
    const val APPLY_MAINTAIN = "logistics/apply/save"

    /**
     * 维修功能：部门树结构
     */
    const val DEPARTMENT_STRUCTURE = "sys/dept/findListByTree "

}