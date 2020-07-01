package com.zhkj.smartpolice.meal

import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.meal.adapter.MealGoodsAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean

class MealRecordActivity : BaseActivity() {

    private val list = ArrayList<MealGoodsBean>()

    private val adapter: MealGoodsAdapter by lazy {
        MealGoodsAdapter(list)
    }

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("订餐记录")

        list.add(MealGoodsBean("1", "酸汤肥牛", 0, 1, "好吃", "￥49.90"))
        list.add(MealGoodsBean("1", "酸菜鱼", 0, 1, "丫米", "￥39.90"))
        list.add(MealGoodsBean("1", "鱼香肉丝", 0, 1, "", "￥29.90"))
        list.add(MealGoodsBean("1", "宫保鸡丁", 0, 1, "", "￥29.90"))
        list.add(MealGoodsBean("1", "手撕包菜", 0, 1, "", "￥9.90"))

        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }


        supportFragmentManager.beginTransaction().replace(getFrameBody().id, pullRefreshFragment).commit()

        showLoading()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        val arrayList = arrayListOf<MealGoodsBean>()
        arrayList.add(MealGoodsBean("1", "酸汤肥牛", 0, 1, "好吃", "￥49.90"))
        arrayList.add(MealGoodsBean("1", "酸菜鱼", 0, 1, "丫米", "￥39.90"))
        arrayList.add(MealGoodsBean("1", "鱼香肉丝", 0, 1, "", "￥29.90"))
        arrayList.add(MealGoodsBean("1", "宫保鸡丁", 0, 1, "", "￥29.90"))
        arrayList.add(MealGoodsBean("1", "手撕包菜", 0, 1, "", "￥9.90"))

        hideLoading()
        pullRefreshFragment.addData(arrayList)
    }

    override fun close() {

    }

}