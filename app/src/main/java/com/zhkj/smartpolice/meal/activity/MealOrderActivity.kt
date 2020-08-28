package com.zhkj.smartpolice.meal.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealOrderAdapter
import com.zhkj.smartpolice.meal.bean.MealBean
import kotlinx.android.synthetic.main.act_meal_order.*

/**
 * 提交订单
 */
class MealOrderActivity : BaseActivity() {
    var total = 0F

    var checkedLock = false

    private val shopId: String by lazy {
        intent.getStringExtra("shopId")
    }

    private val pullRefreshFragment = PullRefreshFragment<MealBean>()

    companion object {
        fun intent(context: Context, shopId: String) {
            val intent = Intent(context, MealOrderActivity::class.java)
            intent.putExtra("shopId", shopId)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_meal_order

    override fun initView() {

        defaultTitle("购物车")
        pullRefreshFragment.enableRefresh = false
        pullRefreshFragment.enableLoadMore = false
        pullRefreshFragment.loadData = {
            loadData()
        }

        ZyFrameStore.getData<ArrayList<MealBean>>("MealGoodsBeanList")?.let {
            pullRefreshFragment.adapter = MealOrderAdapter(object : MealOrderAdapter.OnUpdateListener {
                override fun onUpdate() {
                    checkedLock = true
                    checkbox_all.isChecked = it.none { bean -> !bean.isChecked }
                    checkedLock = false
                    updateShoppingPrice()
                }
            }, it)

            checkbox_all.isChecked = it.none { bean -> !bean.isChecked }
        }

        supportFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshFragment).commit()


        setOnClickListener(tv_commit)

        checkbox_all.setOnCheckedChangeListener { _, isChecked ->

            if (checkedLock) {
                return@setOnCheckedChangeListener
            }

            pullRefreshFragment.getAllData()?.forEach {
                it.isChecked = isChecked
            }
            pullRefreshFragment.adapter?.notifyDataSetChanged()
            updateShoppingPrice()
        }
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_commit.id -> {
                if (pullRefreshFragment.getAllData()?.isEmpty() == true) {
                    return
                }
                val intent = Intent(this, MealOrderInfoActivity::class.java)
                intent.putExtra("shopId", shopId)
                intent.putExtra("total", total)
                startActivity(intent)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun loadData() {
        updateShoppingPrice()
    }

    override fun close() {

    }

    //更新总价
    private fun updateShoppingPrice() {
        pullRefreshFragment.getAllData()?.filter { it.isChecked }?.let { goodsList ->
            total = 0f
            if (goodsList.isEmpty()) {
                tv_total.text = "合计：¥ 0.0"
                tv_commit.setBackgroundColor(Color.parseColor("#CBCBCB"))
            } else {
                goodsList.forEach {
                    it.shopGoodsEntity?.price?.let { price ->
                        total += price.toFloat() * it.count
                    }
                }
                tv_total.text = ("合计：¥ $total")
                tv_commit.setBackgroundResource(R.color.font_orange)
            }

            ZyFrameStore.setData("mealgoodsbeanlist", goodsList)
        }
    }
}