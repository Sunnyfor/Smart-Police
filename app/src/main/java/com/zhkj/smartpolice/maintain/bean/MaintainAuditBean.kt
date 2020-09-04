package com.zhkj.smartpolice.maintain.bean


class MaintainAuditBean {

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
    var shopGoodsEntityList: List<ShopGoodsEntity>? = null
    var shopGoodsId: String? = null
    var shopGoodsName: String? = null
    var repairType: String? = null

    class ShopGoodsEntity {
        var activeState: String? = null
        var classifyId: String? = null
        var createTime: String? = null
        var description: String? = null
        var expiryDate: String? = null
        var goodsClassifyLabel: String? = null
        var goodsId: String? = null
        var goodsName: String? = null
        var groupId: String? = null
        var imageId: String? = null
        var inventory: String? = null
        var isAway: String? = null
        var isDelete: String? = null
        var isTop: String? = null
        var orderNumber: String? = null
        var price: String? = null
        var publishState: String? = null
        var shopGoodsLabelEntityList: Any? = null
        var shopId: String? = null
        var subsidyPrice: String? = null
        var updateUserId: String? = null

        override fun toString(): String {
            return "ShopGoodsEntity(activeState=$activeState, classifyId=$classifyId, createTime=$createTime, description=$description, expiryDate=$expiryDate, goodsClassifyLabel=$goodsClassifyLabel, goodsId=$goodsId, goodsName=$goodsName, groupId=$groupId, imageId=$imageId, inventory=$inventory, isAway=$isAway, isDelete=$isDelete, isTop=$isTop, orderNumber=$orderNumber, price=$price, publishState=$publishState, shopGoodsLabelEntityList=$shopGoodsLabelEntityList, shopId=$shopId, subsidyPrice=$subsidyPrice, updateUserId=$updateUserId)"
        }
    }

    override fun toString(): String {
        return "MaintainAuditBean(applyContent=$applyContent, applyDate=$applyDate, applyId=$applyId, applyState=$applyState, approvalId=$approvalId, approvalName=$approvalName, attachmentGroupId=$attachmentGroupId, createTime=$createTime, createUserId=$createUserId, createUserName=$createUserName, deptId=$deptId, deptName=$deptName, money=$money, petitioner=$petitioner, petitionerPhone=$petitionerPhone, processId=$processId, processName=$processName, publishState=$publishState, repairRecordEntity=$repairRecordEntity, shopGoodsEntityList=$shopGoodsEntityList, shopGoodsId=$shopGoodsId, shopGoodsName=$shopGoodsName, repairType=$repairType)"
    }


}