package com.zhkj.smartpolice.drugstore.bean

/**
 * 药品
 */
class DrugBean {
    var shopId: String? = ""
    var goodsId: String? = ""
    var goodsName: String? = ""
    var price: String? = ""
    var imageId: String? = ""
    var description: String? = ""
    var subsidyPrice: String? = ""
    var activeState: String? = ""
    var publishState: String? = ""
    var groupId: String? = ""
    var classifyId: String? = ""
    var isTop: String? = ""
    var orderNumber: String? = ""
    var createTime: String? = ""
    var updateUserId: String? = ""
    var isAway: String? = ""
    var isDelete: String? = ""
    var inventory: String? = ""
    var expiryDate: String? = ""
    var goodsClassifyLabel: String? = ""

    override fun toString(): String {
        return "MealGoodsBean(shopId=$shopId, goodsId=$goodsId, goodsName=$goodsName, price=$price, description=$description, subsidyPrice=$subsidyPrice, activeState=$activeState, publishState=$publishState, groupId=$groupId, classifyId=$classifyId, isTop=$isTop, orderNumber=$orderNumber, createTime=$createTime, updateUserId=$updateUserId, isAway=$isAway, imageId=$imageId, isDelete=$isDelete, inventory=$inventory, expiryDate=$expiryDate, goodsClassifyLabel=$goodsClassifyLabel)"
    }
}