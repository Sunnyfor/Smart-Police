package com.zhkj.smartpolice.meal.bean

import java.io.Serializable

class MealGoodsBean : Serializable {
    var shopId: String? = ""        //2
    var goodsId: String? = ""       //11
    var goodsName: String? = ""     //菜名：鱼香肉丝
    var price: String? = ""         //菜价：15
    var description: String? = ""   //肉丝
    var subsidyPrice: String? = ""  //null
    var activeState: String? = ""   //1
    var publishState: String? = ""  //1
    var groupId: String? = ""       //null
    var classifyId: String? = ""    //null
    var isTop: String? = ""         //1
    var orderNumber: String? = ""   //1
    var createTime: String? = ""    //2020-05-22 18:42:06
    var updateUserId: String? = ""  //1
    var isAway: String? = ""        //null
    var imageId: String? = ""       //2724
    var isDelete: String? = ""      //2
    var inventory: String? = ""     //库存
    var expiryDate: String? = ""    //null
    var goodsClassifyLabel: String? = ""//null

    /*
     * 【本地字段】商品数量
     */
    var count: Int = 0

    override fun toString(): String {
        return "MealGoodsBean(shopId=$shopId, goodsId=$goodsId, goodsName=$goodsName, price=$price, description=$description, subsidyPrice=$subsidyPrice, activeState=$activeState, publishState=$publishState, groupId=$groupId, classifyId=$classifyId, isTop=$isTop, orderNumber=$orderNumber, createTime=$createTime, updateUserId=$updateUserId, isAway=$isAway, imageId=$imageId, isDelete=$isDelete, inventory=$inventory, expiryDate=$expiryDate, goodsClassifyLabel=$goodsClassifyLabel)"
    }
}