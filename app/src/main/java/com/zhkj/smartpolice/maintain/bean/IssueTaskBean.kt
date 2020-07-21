package com.zhkj.smartpolice.maintain.bean

class IssueTaskBean {
    var content: String? = null
    var groupId: String? = null
    var operation: String?= null
    var operationId: String? = null
    var operationPhone: String? = null
    var professionId: String? = null
    var repairDate: String? = null
    override fun toString(): String {
        return "IssueTaskBean(content=$content, groupId=$groupId, operation=$operation, operationId=$operationId, operationPhone=$operationPhone, professionId=$professionId, repairDate=$repairDate)"
    }

}