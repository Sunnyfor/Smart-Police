package com.zhkj.smartpolice.meal

import android.view.View
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.merchant.MerchantBean
import com.zhkj.smartpolice.merchant.MerchantListActivity
import kotlinx.android.synthetic.main.act_meal_choice.*

class MealChoiceActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_meal_choice

    override fun initView() {

        defaultTitle("餐厅")

        setOnClickListener(
            tv_repast,
            tv_order
        )

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_repast.id -> {
            }
            tv_order.id -> {
                val list = ZyFrameStore.getData<ArrayList<MerchantBean>>("merchantList")
                list?.find { it.shopType == MerchantListActivity.TYPE_RESTAURANT }.apply {
                    MealActivity.intent(this@MealChoiceActivity, this?.shopId)
                }
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}