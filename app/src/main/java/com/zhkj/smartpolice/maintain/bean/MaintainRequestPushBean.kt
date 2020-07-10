package com.zhkj.smartpolice.maintain.bean


class MaintainRequestPushBean {
    var applyContent: String? = null
    var applyDate: String? = null
    var applyId: String? = null
    var applyState: String? = null
    var approvalId: String? = null
    var attachmentGroupId: String? = null
    var createTime: String? = null
    var createUserId: String? = null
    var deptId: String? = null
    var deptName: String? = null
    var money: String? = null
    var petitioner: String? = null
    var petitionerPhone: String? = null
    var processId: String? = null
    var publishState: String? = null
    var repairRecordEntity: RepairRecordEntity? = null
    var shopGoodsId: String? = null

    class RepairRecordEntity{
        var content: String? = null
        var createTime: String? = null
        var createUserId: String? = null
        var createUserName: String? = null
        var groupId: String? = null
        var operation: String? = null
        var operationPhone: String? = null
        var professionId: String? = null
        var recordId: String? = null
        var repairDate: String? = null
    }
}