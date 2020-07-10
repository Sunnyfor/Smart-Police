package com.zhkj.wallet.bean

/**
 * 钱包实体类
 */
data class PurseBean(
    var id: Int, //主键自增ID
    var balance: String, //余额
    var payPassword: String?, //支付密码
    var isExemptPassword: String, //是否免密支付，1是,0否
    var exemptPasswordAmount: String, //免密支付金额
    var userName: String, //用户名
    var userId: String, //用户id
    var createTime: String, //创建时间
    var updateTime: String //修改时间
)