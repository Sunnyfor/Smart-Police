package com.zhkj.smartpolice.meal

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealOrderAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.act_meal_order.*

class MealOrderActivity : BaseActivity() {

    private var list = ArrayList<MealGoodsBean>()

    private val adapter: MealOrderAdapter by lazy {
        MealOrderAdapter(arrayListOf())
    }

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    companion object {
        fun intent(context: Context, list: ArrayList<MealGoodsBean>?) {
            val intent = Intent(context, MealOrderActivity::class.java)
            intent.putExtra("list", list)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_meal_order

    override fun initView() {

        defaultTitle("购物车")

        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshFragment).commit()

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        list = intent.getStringArrayListExtra("list") as ArrayList<MealGoodsBean>
        pullRefreshFragment.addData(list)
    }

    override fun close() {

    }
}