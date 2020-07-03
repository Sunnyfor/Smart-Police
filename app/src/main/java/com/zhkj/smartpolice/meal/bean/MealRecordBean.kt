package com.zhkj.smartpolice.meal.bean

class MealRecordBean {

    var ordersId: String? = null        //订单id
    var ordersNumber: String? = null    //订单号：1592986878000155
    var ordersState: String? = null     //订单状态：1待接单、2已接单、3配送中、4已完成、5拒接、6取消
    var ordersType: String? = null      //订单类型：1自取、2配送、3就餐、4到店购买
    var ordersLinkEntityList: String? = null

    var shopId: String? = null          //店铺id
    var shopName: String? = null        //店铺名称
    var deviceId: String? = null        //商家收款设备id
    var remark: String? = null          //新店开张，优惠多多
    var shippingFee: String? = null     //配送费
    var subsidyPrice: String? = null    //优惠金额

    var mobile: String? = null          //下单手机号
    var createTime: String? = null      //下单时间
    var createUserId: String? = null    //下单人
    var createUserName: String? = null  //下单人姓名
    var shippingAddress: String? = null //收货地址

    var payCode: String? = null    //付款码：011592986867920
    var payUserId: String? = null  //付款用户id
    var totalPrice: String? = null //总金额：20.00
    var payPrice: String? = null   //支付金额：20.00
    var payType: String? = null    //支付方式：1线下、2在线
    var payMethod: String? = null  //付款方式：1支付宝、2微信、3网银、4钱包、5现金、6货到付款
    var payState: String? = null   //支付状态：0待支付、1成功、2关闭、3撤销、4待退款、5退款成功、6交易结束，不可退款

    var payCloseTime: String? = null
    var payRevokeTime: String? = null
    var paySuccessTime: String? = null
    var payRefundTime: String? = null
    var updateTime: String? = null
    var finishTime: String? = null  //完成时间

    var isPayBehalf: String? = null //是否代付：1是、0否
    var isDelete: String? = null    //是否删除：1是、2否

    override fun toString(): String {
        return "MealRecordBean(ordersId=$ordersId, ordersNumber=$ordersNumber, ordersState=$ordersState, ordersType=$ordersType, ordersLinkEntityList=$ordersLinkEntityList, shopId=$shopId, shopName=$shopName, deviceId=$deviceId, remark=$remark, shippingFee=$shippingFee, subsidyPrice=$subsidyPrice, mobile=$mobile, createTime=$createTime, createUserId=$createUserId, createUserName=$createUserName, shippingAddress=$shippingAddress, payCode=$payCode, payUserId=$payUserId, totalPrice=$totalPrice, payPrice=$payPrice, payType=$payType, payMethod=$payMethod, payState=$payState, payCloseTime=$payCloseTime, payRevokeTime=$payRevokeTime, paySuccessTime=$paySuccessTime, payRefundTime=$payRefundTime, updateTime=$updateTime, finishTime=$finishTime, isPayBehalf=$isPayBehalf, isDelete=$isDelete)"
    }

}