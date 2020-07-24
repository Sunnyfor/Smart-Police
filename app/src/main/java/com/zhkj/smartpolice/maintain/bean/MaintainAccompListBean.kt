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
        var repairRecordEntity: Any? = null
        var shopGoodsEntityList: List<ShopGoodsEntity>? = null
        var shopGoodsId: String? = null
        var shopGoodsName: String? = null

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
        }
    }
}