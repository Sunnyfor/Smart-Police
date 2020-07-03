package com.zhkj.smartpolice.maintain.bean


class MaintainClassifyBean {

    var shopId: Int = 0
    var shopName: String? = null
    var shopAddress: String? = null
    var axis: String? = null
    var mobilePhone: String? = null
    var userName: String? = null
    var businessState: Int = 0
    var certifyState: Int = 0
    var activeState: Int = 0
    var classifyId: Int = 0
    var groupId: String? = null
    var remark: String? = null
    var score: String? = null
    var createTime: String? = null
    var createUserId: Int = 0
    var shopType: Int = 0
    var minPrice: String? = null
    var shippingFee: String? = null
    var imageId: String? = null
    var shopLicense: String? = null
    var scope: String? = null
    var allowDate: String? = null
    var deptId: Int = 0
    var treePath: String? = null
    var buffetPrice: Int = 0
    var subsidyPrice: Int = 0
    var businessTime: String? = null
    var classifyName: String? = null
    var deptName: String? = null
    var createNickName: String? = null

    override fun toString(): String {
        return "ClassifyInfoList(shopId=$shopId, shopName=$shopName, shopAddress=$shopAddress, axis=$axis, mobilePhone=$mobilePhone, userName=$userName, businessState=$businessState, certifyState=$certifyState, activeState=$activeState, classifyId=$classifyId, groupId=$groupId, remark=$remark, score=$score, createTime=$createTime, createUserId=$createUserId, shopType=$shopType, minPrice=$minPrice, shippingFee=$shippingFee, imageId=$imageId, shopLicense=$shopLicense, scope=$scope, allowDate=$allowDate, deptId=$deptId, treePath=$treePath, buffetPrice=$buffetPrice, subsidyPrice=$subsidyPrice, businessTime=$businessTime, classifyName=$classifyName, deptName=$deptName, createNickName=$createNickName)"
    }

}