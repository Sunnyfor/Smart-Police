package com.zhkj.smartpolice.meal

import android.content.Intent
import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.meal.adapter.RestaurantAdapter
import com.zhkj.smartpolice.meal.bean.RestaurantBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter

/**
 * 餐厅列表
 */
class RestaurantListActivity : BaseActivity(), MealContract.IRestaurantView {

    private val pullRefreshFragment = PullRefreshFragment<RestaurantBean>()

    private val adapter: RestaurantAdapter by lazy {
        RestaurantAdapter().apply {
            setOnItemClickListener { _, _ ->
                startActivity(Intent(this@RestaurantListActivity, MealActivity::class.java))
            }
        }
    }

    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }


    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("餐厅列表")

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
        presenter.loadRestaurantList()
    }

    override fun close() {

    }

    override fun loadRestaurantList(data: ArrayList<RestaurantBean>) {
        hideLoading()
        pullRefreshFragment.addData(data)
    }

}