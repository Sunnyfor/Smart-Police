package com.zhkj.smartpolice.meal.activity

import android.content.Intent
import android.view.View
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealBean
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import kotlinx.android.synthetic.main.act_meal_order_info.*

/**
 * 订餐信息，填写姓名和手机号
 */
class MealOrderInfoActivity : BaseActivity(), MealContract.IMealPlaceAnOrderView {

    val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    private val shopId: String by lazy {
        intent.getStringExtra("shopId")
    }

    private val total: Float by lazy {
        intent.getFloatExtra("total", 0f)
    }

    private val mealGoodsList: ArrayList<MealBean>? by lazy {
        ZyFrameStore.getData<ArrayList<MealBean>>("MealGoodsBeanList")
    }

    override fun setLayout(): Int = R.layout.act_meal_order_info

    override fun initView() {

        defaultTitle("订餐信息")

        setOnClickListener(tv_confirm)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_confirm.id -> {
                presenter.commitMealOrder(
                    shopId,
                    et_name.text.toString(),
                    et_phone.text.toString(),
                    total.toString(),
                    (mealGoodsList?.filter { it.isChecked }?:return) as ArrayList<MealBean>
                )
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }

    override fun showPlaceAnOrderResult(data: BaseModel<MealRecordBean>) {
        mealGoodsList?.clear()
        ZyFrameStore.finishActivity(MealOrderActivity::class.java.simpleName)
        finish()
        ZyFrameStore.setData(MealRecordBean::class.java.simpleName, data.data)
        startActivity(Intent(this, OrderDetailActivity::class.java))
    }
}