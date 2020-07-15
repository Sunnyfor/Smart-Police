package com.zhkj.smartpolice.maintain.bean


class MaintainAuditBean {

    var applyId: String? = null
    var approvalId: String? = null
    var applyState: String? = null
    var publishState: String? = null
    var shopGoodsId: String? = null
    var petitioner: String? = null
    var petitionerPhone: String? = null
    var deptId: String? = null
    var deptName: String? = null
    var applyDate: String? = null
    var applyContent: String? = null
    var money: String? = null
    var attachmentGroupId: String? = null
    var createUserId: String? = null
    var createTime: String? = null
    var processId: String? = null
    var repairRecordEntity: String? = null
    var approvalName: String? = null
    var shopGoodsName: String? = null
    var createUserName: String? = null
    var processName: String? = null
    override fun toString(): String {
        return "MaintainauditInfo(applyId=$applyId, approvalId=$approvalId, applyState=$applyState, publishState=$publishState, shopGoodsId=$shopGoodsId, petitioner=$petitioner, petitionerPhone=$petitionerPhone, deptId=$deptId, deptName=$deptName, applyDate=$applyDate, applyContent=$applyContent, money=$money, attachmentGroupId=$attachmentGroupId, createUserId=$createUserId, createTime=$createTime, processId=$processId, repairRecordEntity=$repairRecordEntity, approvalName=$approvalName, shopGoodsName=$shopGoodsName, createUserName=$createUserName, processName=$processName)"
    }

}