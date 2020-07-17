package com.zhkj.smartpolice.maintain.bean


class MaintainAccompListBean {
    var applyEntity: ApplyEntity? = null
    var applyId: String? = null
    var createTime: String? = null
    var opinionContent: String? = null
    var opinionId: String? = null
    var opinionType: String? = null
    var optionUserId: String? = null
    var optionUserName: String? = null
    var processId: String? = null
    var processName: String? = null

    override fun toString(): String {
        return "MaintainAccompListBean(applyEntity=$applyEntity, applyId=$applyId, createTime=$createTime, opinionContent=$opinionContent, opinionId=$opinionId, opinionType=$opinionType, optionUserId=$optionUserId, optionUserName=$optionUserName, processId=$processId, processName=$processName)"
    }

    class ApplyEntity {
        var applyContent: String? = null
        var applyDate: String? = null
        var applyId: String? = null
        var applyState: String? = null
        var approvalId: String? = null
        var approvalName: String? = null
        var attachmentGroupId: String? = null
        var createTime: String? = null
        var createUserId: String? = null
        var createUserName: String? = null
        var deptId: String? = null
        var deptName: String? = null
        var money: String? = null
        var petitioner: String? = null
        var petitionerPhone: String? = null
        var processId: String? = null
        var processName: String? = null
        var publishState: String? = null
        var repairRecordEntity: String? = null
        var shopGoodsId: String? = null
        var shopGoodsName: String? = null
        override fun toString(): String {
            return "ApplyEntity(applyContent=$applyContent, applyDate=$applyDate, applyId=$applyId, applyState=$applyState, approvalId=$approvalId, approvalName=$approvalName, attachmentGroupId=$attachmentGroupId, createTime=$createTime, createUserId=$createUserId, createUserName=$createUserName, deptId=$deptId, deptName=$deptName, money=$money, petitioner=$petitioner, petitionerPhone=$petitionerPhone, processId=$processId, processName=$processName, publishState=$publishState, repairRecordEntity=$repairRecordEntity, shopGoodsId=$shopGoodsId, shopGoodsName=$shopGoodsName)"
        }
    }

}
