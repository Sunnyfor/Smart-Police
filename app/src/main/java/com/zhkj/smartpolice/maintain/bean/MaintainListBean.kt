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
    var linkId: Any,
    var publishState: Int,
    var shopId: Int,
    var sort: Int

) {
    override fun toString(): String {
        return "MaintainListBean(checked=$checked, createTime='$createTime', createUserId=$createUserId, createUserName='$createUserName', goodsList=$goodsList, labelId=$labelId, labelName='$labelName', labelType=$labelType, linkId=$linkId, publishState=$publishState, shopId=$shopId, sort=$sort)"
    }
}

data class Goods(
    var activeState: Int,
    var classifyId: Any,
    var createTime: String,
    var description: String,
    var expiryDate: Any,
    var goodsClassifyLabel: Any,
    var goodsId: Int,
    var goodsName: String,
    var groupId: Any,
    var imageId: Any,
    var inventory: Any,
    var isAway: Any,
    var isDelete: Int,
    var isTop: Int,
    var orderNumber: Int,
    var price: Double,
    var publishState: Int,
    var shopGoodsLabelEntityList: Any,
    var shopId: Int,
    var subsidyPrice: Any,
    var updateUserId: Int

) {
    override fun toString(): String {
        return "Goods(activeState=$activeState, classifyId=$classifyId, createTime='$createTime', description='$description', expiryDate=$expiryDate, goodsClassifyLabel=$goodsClassifyLabel, goodsId=$goodsId, goodsName='$goodsName', groupId=$groupId, imageId=$imageId, inventory=$inventory, isAway=$isAway, isDelete=$isDelete, isTop=$isTop, orderNumber=$orderNumber, price=$price, publishState=$publishState, shopGoodsLabelEntityList=$shopGoodsLabelEntityList, shopId=$shopId, subsidyPrice=$subsidyPrice, updateUserId=$updateUserId)"
    }
}