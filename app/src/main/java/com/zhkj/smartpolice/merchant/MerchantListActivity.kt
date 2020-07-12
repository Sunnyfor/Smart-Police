package com.zhkj.smartpolice.merchant

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.stadium.StadiumDetailActivity
import kotlinx.coroutines.cancel

/**
 * 商家列表
 */
class MerchantListActivity : BaseActivity(), MerchantContract.IMerchantView {

    private var shopType: String? = null

    private val pullRefreshFragment = PullRefreshFragment<MerchantBean>()

    private val adapter: MerchantAdapter by lazy {
        MerchantAdapter(shopType ?: "").apply {
            setOnItemClickListener { _, _ ->

                if (shopType == TYPE_STADIUM){
                    startActivity(Intent(this@MerchantListActivity, StadiumDetailActivity::class.java))
                }

//                startActivity(Intent(this@MerchantListActivity, MealActivity::class.java))
            }
        }
    }

    private val presenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    companion object {

        const val TYPE_RESTAURANT = "1"   //餐厅
        const val TYPE_DRUGSTORE = "2"    //药店
        const val TYPE_HAIRCUT = "3"      //理发店
        const val TYPE_LAUNDRY = "4"      //洗衣店
        const val TYPE_STADIUM = "5"      //运动场馆
        const val TYPE_POLICE = "6"       //警营
        const val TYPE_SHUTTLE_BUS = "7"  //班车

        fun intent(context: Context, shopType: String) {
            val intent = Intent(context, MerchantListActivity::class.java)
            intent.putExtra("shopType", shopType)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        shopType = intent.getStringExtra("shopType")


        val title = when (shopType) {
            TYPE_RESTAURANT -> "餐厅"
            TYPE_DRUGSTORE -> "药店"
            TYPE_HAIRCUT -> "理发店"
            TYPE_LAUNDRY -> "洗衣店"
            TYPE_STADIUM -> "运动场馆"
            TYPE_POLICE -> "警营"
            TYPE_SHUTTLE_BUS -> "班车"
            else -> "商家"
        }

        defaultTitle("${title}列表")

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
        presenter.loadMerchantList(pullRefreshFragment.page.toString(), shopType ?: return)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showMerchantList(data: ArrayList<MerchantBean>) {
        hideLoading()
        pullRefreshFragment.addData(data)
    }

}