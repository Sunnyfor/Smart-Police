package com.zhkj.smartpolice.maintain.bean


data class MaintainListBean(
    var checked: Boolean,
    var createTime: String,
    var createUserId: Int,
    var createUserName: String,
    var goodsList: List<Goods>,
    var labelId: Int,
    var labelName: String,
    var labelType: Int,
    var linkId: String,
    var publishState: Int,
    var shopId: Int,
    var sort: Int

) {
    override fun toString(): String {
        return "MaintainListBean(checked=$checked, createTime='$createTime', createUserId=$createUserId, createUserName='$createUserName', goodsList=$goodsList, labelId=$labelId, labelName='$labelName', labelType=$labelType, linkId=$linkId, publishState=$publishState, shopId=$shopId, sort=$sort)"
    }
}

data class Goods(
    var activeState: String?,
    var classifyId: String?,
    var createTime: String?,
    var description: String?,
    var expiryDate: String?,
    var goodsClassifyLabel: String?,
    var goodsId: String?,
    var goodsName: String?,
    var groupId: String?,
    var imageId: String?,
    var inventory: String?,
    var isAway: String?,
    var isDelete: String?,
    var isTop: String?,
    var orderNumber: String?,
    var price: Double,
    var publishState: String?,
    var shopGoodsLabelEntityList: String?,
    var shopId: String?,
    var subsidyPrice: String?,
    var updateUserId: String?

) {
    override fun toString(): String {
        return "Goods(activeState=$activeState, classifyId=$classifyId, createTime='$createTime', description='$description', expiryDate=$expiryDate, goodsClassifyLabel=$goodsClassifyLabel, goodsId=$goodsId, goodsName='$goodsName', groupId=$groupId, imageId=$imageId, inventory=$inventory, isAway=$isAway, isDelete=$isDelete, isTop=$isTop, orderNumber=$orderNumber, price=$price, publishState=$publishState, shopGoodsLabelEntityList=$shopGoodsLabelEntityList, shopId=$shopId, subsidyPrice=$subsidyPrice, updateUserId=$updateUserId)"
    }
}