package com.zhkj.wallet.bean

/**
 * Desc 银行卡
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/12/7 11:04
 */
class BandCardBean {
    var id: String? = null
    var userId: String? = null
    var bandCard: String? = null
    var idCard: String? = null
    var name: String? = null
    var bankName: String? = null
    var createTime: String? = null

    fun isEmpty(): Boolean {
        if (id != null) {
            return false
        }

        if (userId != null) {
            return false
        }

        if (bandCard != null) {
            return false
        }

        if (idCard != null) {
            return false
        }

        if (name != null) {
            return false
        }
        if (bankName != null) {
            return false
        }
        if (createTime != null) {
            return false
        }

        return true
    }
}