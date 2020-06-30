package com.zhkj.smartpolice.drugstore

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealGoodsAdapter
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import kotlinx.android.synthetic.main.act_meal.*

class DrugstoreActivity : BaseActivity() {

    private val menuList = arrayListOf<MealMenuBean>()
    private val goodsList = arrayListOf<MealGoodsBean>()

    override fun setLayout(): Int = R.layout.act_drugstore

    override fun initView() {

        defaultTitle("订餐列表")

        menuList.add(MealMenuBean("1", "营养成分", 1))
        menuList.add(MealMenuBean("2", "营养健康", 0))
        menuList.add(MealMenuBean("3", "保健机械", 0))
        menuList.add(MealMenuBean("4", "护理护具", 0))
        menuList.add(MealMenuBean("5", "传统滋补", 0))
        menuList.add(MealMenuBean("6", "隐形眼镜", 0))

        recyclerView_menu.layoutManager = LinearLayoutManager(this)
        recyclerView_menu.adapter = MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                notifyDataSetChanged()
            }
        }

        goodsList.add(MealGoodsBean("1", "感冒药", 0, 1, "好吃", "￥49.90"))
        goodsList.add(MealGoodsBean("1", "感冒药", 0, 1, "丫米", "￥39.90"))

        recyclerView_goods.layoutManager = GridLayoutManager(this, 2)
        recyclerView_goods.adapter = MealGoodsAdapter(goodsList).apply {
            setOnItemClickListener { _, i ->
            }
        }

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
        }
    }

    override fun loadData() {

    }

    override fun close() {
    }
}