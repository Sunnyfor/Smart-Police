package com.zhkj.smartpolice.app.data

import androidx.lifecycle.ViewModel
import com.zhkj.smartpolice.merchant.MerchantBean


class MerchantViewModel : ViewModel() {

    /**
     * 商家列表信息
     */
    var list = ArrayList<MerchantBean>()


}