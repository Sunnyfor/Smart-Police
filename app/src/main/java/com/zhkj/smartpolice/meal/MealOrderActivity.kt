package com.zhkj.smartpolice.meal

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealOrderAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.act_meal_order.*

class MealOrderActivity : BaseActivity() {
    var total = 0F

    private val shopId: String by lazy {
        intent.getStringExtra("shopId")
    }

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    companion object {
        fun intent(context: Context, shopId: String, list: ArrayList<MealGoodsBean>?) {
            ZyFrameStore.setData("MealGoodsBeanList", list)
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

        ZyFrameStore.getData<ArrayList<MealGoodsBean>>("MealGoodsBeanList")?.let {
            pullRefreshFragment.adapter = MealOrderAdapter(object : MealOrderAdapter.OnUpdateListener {
                override fun onUpdate() {
                    updateShoppingPrice()
                }
            }, it)
        }

        supportFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshFragment).commit()


        setOnClickListener(tv_commit)
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
        pullRefreshFragment.getAllData()?.let { goodsList ->
            total = 0f
            if (goodsList.isEmpty()) {
                tv_total.text = "合计：¥ 0.0"
                tv_commit.setBackgroundColor(Color.parseColor("#CBCBCB"))
            } else {
                goodsList.forEach {
                    it.price?.let { price ->
                        total += price.toFloat() * it.count
                    }
                }
                tv_total.text = ("合计：¥ $total")
                tv_commit.setBackgroundResource(R.color.font_orange)
            }

            ZyFrameStore.setData("MealGoodsBeanList", goodsList)
        }
    }
}