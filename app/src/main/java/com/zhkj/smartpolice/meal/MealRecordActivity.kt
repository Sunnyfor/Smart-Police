package com.zhkj.smartpolice.meal

import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.meal.adapter.MealRecordAdapter
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import kotlinx.coroutines.cancel

/**
 * 订餐记录
 */
class MealRecordActivity : BaseActivity(), MealContract.IMealRecordView {

    private val pullRefreshFragment = PullRefreshFragment<MealRecordBean>()

    private val adapter = MealRecordAdapter()

    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("订餐记录")


        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(getFrameBody().id, pullRefreshFragment).commit()

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        showLoading()
        presenter.loadMealRecord(pullRefreshFragment.page.toString())
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showMealRecord(data: ArrayList<MealRecordBean>) {
        hideLoading()
        pullRefreshFragment.addData(data)
    }

}