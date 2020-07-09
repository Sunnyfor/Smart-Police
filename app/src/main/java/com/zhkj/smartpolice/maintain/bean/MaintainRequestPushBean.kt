package com.zhkj.smartpolice.maintain.bean


data class MaintainRequestPushBean(
    var applyContent: String,
    var applyDate: String,
    var applyId: Int,
    var applyState: Int,
    var approvalId: Int,
    var attachmentGroupId: String,
    var createTime: String,
    var createUserId: Int,
    var deptId: Int,
    var deptName: String,
    var money: Int,
    var petitioner: String,
    var petitionerPhone: String,
    var processId: Int,
    var publishState: Int,
    var repairRecordEntity: RepairRecordEntity,
    var shopGoodsId: Int
)

data class RepairRecordEntity(
    var content: String,
    var createTime: String,
    var createUserId: Int,
    var createUserName: String,
    var groupId: Int,
    var operation: String,
    var operationPhone: String,
    var professionId: Int,
    var recordId: Int,
    var repairDate: String
)