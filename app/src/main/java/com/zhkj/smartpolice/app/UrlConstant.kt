package com.zhkj.smartpolice.app

import com.sunny.zy.http.UrlConstant

object UrlConstant {

    /**
     * 图片下载地址
     */
    const val LOAD_IMAGE_PATH_URL = "${UrlConstant.host}/sys/attachment/download?attID="

    /**
     * 图片上传地址
     */
    const val UPLOAD_IMAGE_PATH_URL = "sys/attachment/upload"

    /**
     * 图片查询地址
     */
    const val FIND_IMAGE_PATH_URL = "sys/attachment/list"

    /**
     * 药店模块：意见箱
     */
    const val DRUGSTORE_FEEDBACK_URL = "logistics/feedback/save"

    /**
     * 餐厅模块：餐厅列表
     */
    const val RESTAURANT_LIST_URL = "logistics/shop/list"

    /**
     * 餐厅模块：餐厅菜谱分类
     */
    const val MEAL_MENU_CLASSIFY_URL = "logistics/shopgoodslabel/list"

    /**
     * 餐厅模块：餐厅菜品列表
     * 药店模块：药品列表
     */
    const val MEAL_GOODS_LIST_URL = "logistics/shopgoods/list"

    /**
     * 餐厅模块：订餐记录
     */
    const val MEAL_RECORD_URL = "logistics/shoporders/list"


    /**
     * 下单接口
     */
    const val PLACE_AN_ORDER = "logistics/shoporders/saveForUser"

    /**
     * 商品详情页
     */
    const val RESTAURANT_INFO_URL = "logistics/shop/info/%s"

    /**
     * 预约时间（理发店、运动场）
     */
    const val LIST_RESOURCE_MANAGE_TIME_URL = "logistics/reservemanage/listResourceManageTime"

    /**
     * 预约资源
     */
    const val RESERVE_RESOURCE_LIST_URL = "logistics/reserveresource/list"

    /**
     * 普通警员预约
     */
    const val SAVE_RECORD_URL = "logistics/reserverecord/saveRecord"


    /**
     * 领导预约
     */
    const val RESERVE_RECORD_SAVE_URL = "logistics/reserverecord/save"


    /**
     * 个人中心：用户信息
     */
    const val LOAD_USER_INFO_URL = "sys/user/info"

    /**
     * 个人中心：用户信息更新
     */
    const val UPDATE_USER_INFO_URL = "sys/user/updateInfo"

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

    /**
     * 维修功能：审批维修清单
     */
    const val MAINTAIN_AUDIT = "logistics/apply/list"

    /**
     * 维修功能：维修管理审批请求
     */
    const val MAINTAIN_AUDIT_FEEDBACK = "logistics/applyopinion/save"

    /**
     * 维修功能：审核完成
     */
    const val MAINIAIN_AUDIT_FINISH = "logistics/applyopinion/list"

    /**
     * 维修工人列表
     */
    const val MAINTAINER_LIST = "sys/user/list"

    /**
     * 下发任务给维修工人
     */
    const val ISSUE_TASK = "logistic/repairrecord/save"

    /**
     * 查询指派给我的维修（维修工人）
     */
    const val MAINTAIN_TASK = "logistic/repairrecord/listMyRepair"

    /**
     * 维修工人提交维修成果
     */
    const val MAINTAIN_FINISH = "logistic/repairrecord/update"

    /**
     * 通知
     */
    const val NOTICE_LIST_URL = "app/appnotice/list"

    /**
     * 读取通知
     */
    const val UPDATE_BY_NOTICE_ID_URL = "app/appnoticeuserlink/updateByNoticeId"

    /**
     * 班车模块：班车信息列表
     */
    const val SHUTTLE_BUS_LIST_URL = "logistics/shop/listMyShop?shopType=%s"

}