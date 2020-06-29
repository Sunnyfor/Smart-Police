package com.zhkj.smartpolice.meal

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshLayoutFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealOrderAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.act_meal_order.*

class MealOrderActivity : BaseActivity() {

    private val list = ArrayList<MealGoodsBean>()

    private val adapter: MealOrderAdapter by lazy {
        MealOrderAdapter(list)
    }

    private val pullRefreshLayoutFragment: PullRefreshLayoutFragment by lazy {
        PullRefreshLayoutFragment()
    }


    override fun setLayout(): Int = R.layout.act_meal_order

    override fun initView() {

        defaultTitle("购物车")
        pullRefreshLayoutFragment.setLayoutManager(LinearLayoutManager(this))
        pullRefreshLayoutFragment.loadData = {
            loadData()
        }
        pullRefreshLayoutFragment.setBaseRecyclerAdapter(adapter)
        supportFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshLayoutFragment).commit()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

        list.add(MealGoodsBean("1", "酸汤肥牛", 0, 1, "好吃", "￥49.90"))
        list.add(MealGoodsBean("1", "酸菜鱼", 0, 1, "丫米", "￥39.90"))
        list.add(MealGoodsBean("1", "鱼香肉丝", 0, 1, "", "￥29.90"))
        list.add(MealGoodsBean("1", "宫保鸡丁", 0, 1, "", "￥29.90"))
        list.add(MealGoodsBean("1", "手撕包菜", 0, 1, "", "￥9.90"))

        pullRefreshLayoutFragment.addData(list)
    }

    override fun close() {

    }
}