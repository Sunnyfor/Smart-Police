package com.zhkj.smartpolice.merchant

class MerchantBean {

    var shopId: String? = null          //店铺id
    var shopName: String? = null        //店铺名称
    var shopAddress: String? = null     //店铺地址
    var userName: String? = null        //店家姓名
    var mobilePhone: String? = null     //店家联系方式
    var imageId: String? = null         //店铺Logo
    var businessTime: String? = null    //营业时间："06:00 - 09:00,11:00 - 14:00,17:00 - 21:00"
    var remark: String? = null          //新店开张，优惠多多
    var buffetPrice: String? = null     //起送费
    var subsidyPrice: String? = null    //优惠金额
    var shippingFee: String? = null     //配送费
    var scope: String? = null           //经营范围：各种美食
    var axis: String? = null
    var businessState: String? = null   //1,
    var certifyState: String? = null    //1,
    var activeState: String? = null     //1,
    var classifyId: String? = null      //430,
    var classifyName: String? = null    //"餐厅",
    var groupId: String? = null         //"1589541135498",
    var score: String? = null           //null,
    var createTime: String? = null      //"2020-05-09 07:10:59",
    var createUserId: String? = null    //null,
    var shopType: String? = null        //1,
    var minPrice: String? = null        //null,
    var shopLicense: String? = null     //"",
    var allowDate: String? = null       //"2021-08-24",
    var deptId: String? = null          //31,
    var treePath: String? = null        //"-1/1/31/",
    var deptName: String? = null        //"北京贞和科技",
    var createNickName: String? = null  //null

    override fun toString(): String {
        return "RestaurantBean(shopId=$shopId, shopName=$shopName, shopAddress=$shopAddress, userName=$userName, mobilePhone=$mobilePhone, imageId=$imageId, businessTime=$businessTime, remark=$remark, buffetPrice=$buffetPrice, subsidyPrice=$subsidyPrice, axis=$axis, businessState=$businessState, certifyState=$certifyState, activeState=$activeState, classifyId=$classifyId, classifyName=$classifyName, groupId=$groupId, score=$score, createTime=$createTime, createUserId=$createUserId, shopType=$shopType, minPrice=$minPrice, shippingFee=$shippingFee, shopLicense=$shopLicense, scope=$scope, allowDate=$allowDate, deptId=$deptId, treePath=$treePath, deptName=$deptName, createNickName=$createNickName)"
    }

}