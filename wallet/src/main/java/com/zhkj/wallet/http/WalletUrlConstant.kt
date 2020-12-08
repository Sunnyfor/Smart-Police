package com.zhkj.wallet.http

import com.sunny.zy.http.UrlConstant

object WalletUrlConstant {
    /**
     * 钱包URL
     */
    const val PAY_PURSE = "pay/purse/getPurse"

    /**
     * 生成支付码
     */
    const val PAY_GENERATE_QR_CODE = "pay/purse/generatePayQrcode"

    /**
     * 支付记录
     */
    const val PAY_RECORD_LIST = "pay/record/list"

    /**
     *  创建/修改支付密码
     */
    const val UPDATE_PAY_PASSWORD = "pay/purse/updatePayPassword"

    /**
     * 验证支付密码
     */
    const val VERIFY_PAY_PASSWORD = "pay/purse/verifyPayPassword"

    /**
     * 支付
     */
    const val PAY_FOR_USER_URL = "logistics/shoporders/payForUser"

    /**
     * 支付码长连接地址
     */
    const val WEB_SOCKET_HOST = "ws://" + UrlConstant.IP + "/websocket/%s"

    /**
     * 获取第三方支付参数
     */
    const val PAY_RECORD_SAVE = "pay/record/save"

    /**
     * 查询银行卡列表
     */
    const val BAND_CARD_LIST_URL = "pay/bandcard/list"

    /**
     *  添加银行卡
     */
    const val BAND_CARD_SAVE_URL = "pay/bandcard/save"

    /**
     * 提现接口
     */
    const val RECORD_QMF_PAYMENT = "pay/record/qmfPayment"

    /**
     *  验证码
     */
    const val SEND_VERIFICATION_CODE_URL = "sys/user/open/sendVerificationCode"

}