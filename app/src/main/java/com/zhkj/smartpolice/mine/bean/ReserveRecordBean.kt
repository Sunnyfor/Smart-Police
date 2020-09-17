package com.zhkj.smartpolice.mine.bean


class ReserveRecordBean {

    val shopId: String? = null
    val beginTime: String? = null
    val createTime: String? = null
    val createUserId: String? = null
    val endTime: String? = null
    val feedback: String? = null
    val groupId: String? = null
    val isDelete: String? = null
    val manageId: String? = null
    val mobile: String? = null
    val ordersId: String? = null
    val remark: String? = null
    val reserveId: String? = null
    val reserveNumber: String? = null
    val reserveState: String? = null
    val reserveTime: String? = null
    val reserveType: String? = null
    val reserveUserName: String? = null
    val manageEntity: ManageEntity? = null
    val resourceEntity: ResourceEntity? = null
    var shopName: String? = null

    class ManageEntity {
        val shopId: String? = null
        val activeState: String? = null
        val beginTime: String? = null
        val createTime: String? = null
        val createUserId: String? = null
        val endTime: String? = null
        val manageDate: String? = null
        val manageId: String? = null
        val manageTime: String? = null
        val remark: String? = null
        val reserveNumber: String? = null
        val resourceId: String? = null
        val resourceName: String? = null
        val setNumber: String? = null
    }

    class ResourceEntity {
        val shopId: String? = null
        val shopName: String? = null
        val activeState: String? = null
        val classifyId: String? = null
        val createTime: String? = null
        val createUserId: String? = null
        val deptQuota: String? = null
        val groupId: String? = null
        val imageId: String? = null
        val isTop: String? = null
        val manageDate: String? = null
        val manageList: String? = null
        val orderNumber: String? = null
        val resourceContext: String? = null
        val resourceId: String? = null
        val resourceLevel: String? = null
        val resourceName: String? = null
        val selfQuota: String? = null
    }
}