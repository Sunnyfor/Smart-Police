package com.zhkj.smartpolice.haircut.bean

import java.io.Serializable

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/14 15:33
 */
data class ManageBean( var resourceId: String? = null,
                       var resourceName: String? = null,
                       var resourceContext: String? = null,
                       var resourceLevel: String? = null,
                       var shopId: String? = null,
                       var classifyId: String? = null,
                       var groupId: String? = null,
                       var activeState: String? = null,
                       var isTop: String? = null,
                       var orderNumber: String? = null,
                       var createUserId: String? = null,
                       var createTime: String? = null,
                       var imageId: String? = null,
                       var shopName: String? = null,
                       var manageList: String? = null,
                       var manageDate: String? = null):Serializable