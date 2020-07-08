package com.zhkj.smartpolice.app

import com.sunny.zy.http.UrlConstant

object UrlConstant {

    /**
     * 餐厅模块：订餐记录
     */
    const val MEAL_RECORD_URL = "logistics/shoporders/list"

    /**
     * 餐厅模块：餐厅列表
     */
    const val RESTAURANT_LIST_URL = "logistics/shop/list"

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

}