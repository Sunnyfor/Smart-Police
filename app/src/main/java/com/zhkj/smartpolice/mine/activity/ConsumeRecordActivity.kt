package com.zhkj.smartpolice.mine.activity

import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.meal.adapter.MealRecordAdapter
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter

class ConsumeRecordActivity : BaseActivity(), MealContract.IMealRecordView {

    val pullRefreshFragment = PullRefreshFragment<MealRecordBean>()

    val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("消费记录")

        pullRefreshFragment.adapter = MealRecordAdapter()
        pullRefreshFragment.loadData = {
            loadData()
        }
        supportFragmentManager.beginTransaction().add(getFrameBody().id, pullRefreshFragment).commit()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        presenter.loadMealRecord(pullRefreshFragment.page, true)
    }

    override fun close() {

    }

    override fun showMealRecord(data: ArrayList<MealRecordBean>) {
        pullRefreshFragment.addData(data)
    }
}