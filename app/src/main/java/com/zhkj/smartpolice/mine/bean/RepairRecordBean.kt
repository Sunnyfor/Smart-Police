package com.zhkj.smartpolice.mine.bean

/**
 * 维修记录
 */
class RepairRecordBean {

    val applyContent: String? = null    //申请标题：申请测试
    val applyDate: String? = null
    val applyId: String? = null
    val applyState: String? = null
    val approvalId: String? = null
    val attachmentGroupId: String? = null
    val createTime: String? = null      //申请时间
    val createUserId: String? = null
    val deptId: String? = null
    val deptName: String? = null
    val money: String? = null
    val petitioner: String? = null      //申请人
    val petitionerPhone: String? = null //申请人手机号
    val processId: String? = null
    val publishState: String? = null
    val shopGoodsId: String? = null
    val repairRecordEntity: RepairRecordEntity? = null

    class RepairRecordEntity {
        val content: String? = null         //维修记录内容
        val createTime: String? = null      //维修时间
        val createUserId: String? = null
        val createUserName: String? = null
        val groupId: String? = null
        val operation: String? = null
        val operationPhone: String? = null
        val professionId: String? = null
        val recordId: String? = null
        val repairDate: String? = null
    }
}