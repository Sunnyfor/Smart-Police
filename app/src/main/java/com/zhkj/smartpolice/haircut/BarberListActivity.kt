package com.zhkj.smartpolice.haircut

import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.meal.adapter.RestaurantAdapter
import com.zhkj.smartpolice.meal.bean.RestaurantBean
import com.zhkj.smartpolice.merchant.MerchantPresenter
import kotlinx.coroutines.cancel

/**
 * 理发师列表
 */
class BarberListActivity : BaseActivity(){

    private val pullRefreshFragment = PullRefreshFragment<RestaurantBean>()

    private val adapter: RestaurantAdapter by lazy {
        RestaurantAdapter().apply {
            setOnItemClickListener { _, _ ->
//                startActivity(Intent(this@RestaurantListActivity, MealActivity::class.java))
            }
        }
    }

    private val presenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("理发师列表")


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
        presenter.loadMerchantList(pullRefreshFragment.page.toString(), "3")
    }

    override fun close() {
        presenter.cancel()
    }


}