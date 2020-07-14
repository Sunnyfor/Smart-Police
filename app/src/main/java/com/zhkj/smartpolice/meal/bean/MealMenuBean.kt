package com.zhkj.smartpolice.meal.bean

class MealMenuBean {
    var shopId: String? = ""        //7
    var labelId: String? = ""       //11
    var labelName: String? = ""     //抗菌消炎
    var labelType: String? = ""     //1
    var sort: String? = ""          //1
    var publishState: String? = ""  //1
    var createUserName: String? = ""//管理员
    var createUserId: String? = ""  //1
    var createTime: String? = ""    //2020-06-29 16:45:45"
    var linkId: String? = ""        //null
    var checked: String? = ""       //false
    var goodsList: String? = ""     //null

    override fun toString(): String {
        return "MealMenuBean(shopId=$shopId, labelId=$labelId, labelName=$labelName, labelType=$labelType, sort=$sort, publishState=$publishState, createUserName=$createUserName, createUserId=$createUserId, createTime=$createTime, linkId=$linkId, checked=$checked, goodsList=$goodsList)"
    }
}