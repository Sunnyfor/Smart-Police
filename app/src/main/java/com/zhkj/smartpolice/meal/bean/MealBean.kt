package com.zhkj.smartpolice.meal.bean

/**
 * 菜品
 */
class MealBean {

    var id: String? = null
    var shopId: String? = null
    var goodsId: String? = null
    var date: String? = null
    var total: String? = null
    var remaining: String? = null //剩余份数
    var type: String? = null
    var classify: String? = null
    var createTime: String? = null
    var createBy: String? = null
    var updateTime: String? = null
    var updateBy: String? = null
    var shopGoodsEntity: MealGoodsBean? = null

    class MealGoodsBean {
        var goodsId: String? = null
        var goodsName: String? = null
        var description: String? = null
        var price: String? = null
        var subsidyPrice: String? = null
        var activeState: String? = null
        var publishState: String? = null
        var shopId: String? = null
        var groupId: String? = null
        var classifyId: String? = null
        var isTop: String? = null
        var orderNumber: String? = null
        var createTime: String? = null
        var updateUserId: String? = null
        var isAway: String? = null
        var imageId: String? = null
        var isDelete: String? = null
        var inventory: String? = null
        var expiryDate: String? = null
        var goodsClassifyLabel: String? = null
        var shopGoodsLabelEntityList: String? = null
        var piece = 1
    }

    /*
     * 【本地字段】商品数量
     */
    var count: Int = 1
    var isChecked = true

}