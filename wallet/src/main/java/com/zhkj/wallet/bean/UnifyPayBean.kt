package com.zhkj.wallet.bean

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/8/25 18:19
 */
class UnifyPayBean {
    var id: String? = null
    var isRecharge: String? = null
    var type: String? = null
    var qmfPayType: String? = null
    var buyerLogonId: String? = null
    var buyerUserId: String? = null
    var wxOpenid: String? = null
    var param: String? = null
    var notifyUrlParam: String? = null
    var wechatPrePaymentOrderParam: String? = null
    var amount: String? = null
    var orderId: String? = null
    var tradeNo: String? = null
    var body: String? = null
    var refundOrderNo: String? = null
    var refundAmount: String? = null
    var refundReason: String? = null
    var status: String? = null
    var createUserId: String? = null
    var createUserName: String? = null
    var createTime: String? = null
    var updateTime: String? = null
    var paySuccessTime: String? = null
    var payCloseTime: String? = null
    var payRevokeTime: String? = null
    var payRefundTime: String? = null
    var appid: String? = null
}

//{
//    "id":103,
//    "isRecharge":1,
//    "type":6,
//    "qmfPayType":2,
//    "buyerLogonId":null,
//    "buyerUserId":null,
//    "wxOpenid":null,
//    "param":"{"totalAmount":"100","requestTimestamp":"2020-08-25 18:18:33","mid":"898310148160568","tid":"00000001","orderDesc":"钱包充值","merOrderId":"10171598350713000139","instMid":"APPDEFAULT"}",
//    "notifyUrlParam":null,
//    "wechatPrePaymentOrderParam":"[{"全民付预支付订单响应参数":"{\"responseTimestamp\":\"2020-08-25 18:18:33\",\"errCode\":\"BAD_REQUEST\",\"errMsg\":\"参数不合法: subAppId 不能为null,\"}"}]",
//    "amount":"100",
//    "orderId":"10171598350713000139",
//    "tradeNo":null,
//    "body":"充值",
//    "refundOrderNo":null,
//    "refundAmount":null,
//    "refundReason":null,
//    "status":0,
//    "createUserId":1,
//    "createUserName":"admin",
//    "createTime":"2020-08-25 18:18:33",
//    "updateTime":null,
//    "paySuccessTime":null,
//    "payCloseTime":null,
//    "payRevokeTime":null,
//    "payRefundTime":null,
//    "appid":"10037e6f66f2d0f901672aa27d690006"
//}