package com.zhkj.wallet.http

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
}