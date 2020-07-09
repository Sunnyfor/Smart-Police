package com.zhkj.smartpolice.maintain.activity

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.PageModel
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.adapter.MaintainClassifyAdapter
import com.zhkj.smartpolice.maintain.adapter.PartsListAdapter
import com.zhkj.smartpolice.maintain.bean.Goods
import com.zhkj.smartpolice.maintain.bean.MaintainClassifyBean
import com.zhkj.smartpolice.maintain.bean.MaintainListBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.act_police_maintain.*

class PoliceMaintainActivity : BaseActivity(), IMaintainView {
    private var maintainClassifyList: ArrayList<MaintainListBean> = ArrayList()
    private var goodsList: ArrayList<Goods> = ArrayList()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    /**
     * 左侧分类栏适配
     */
    private val adapter: MaintainClassifyAdapter by lazy {
        MaintainClassifyAdapter(maintainClassifyList).apply {
            setOnItemClickListener { _, position ->
                LogUtil.i("点击了这条Item的数据=========${getData(position).goodsList}")
                goodsList.clear()
                goodsList.addAll(getData(position).goodsList)
                partsAdapter.notifyDataSetChanged()
            }
        }
    }


    private val partsAdapter: PartsListAdapter by lazy {
        PartsListAdapter(goodsList).apply {
            setOnItemClickListener { _, position ->
                MaintainApplyActivity.intent(
                    this@PoliceMaintainActivity,
                    getData(position).activeState,
                    getData(position).classifyId,
                    getData(position).createTime,
                    getData(position).goodsName
                )
            }
        }
    }


    override fun setLayout(): Int = R.layout.act_police_maintain

    override fun initView() {
        defaultTitle("维修部件")
        rv_left_navigation.layoutManager = layoutManager
        rv_left_navigation.adapter = adapter
        rv_parts.layoutManager = GridLayoutManager(this, 2)
        rv_parts.adapter = partsAdapter
    }

    override fun onClickEvent(view: View) {
        when (view.id) {

        }
    }

    override fun loadData() {
        maintainPresenter.onMaintainClassify(6, 1, 1)
    }

    override fun close() {

    }

    override fun onMaintainClassify(pageModel: PageModel<MaintainClassifyBean>) {
        super.onMaintainClassify(pageModel)
        pageModel.let {
            it.data?.let { data ->
                maintainPresenter.onMaintainList(data.list.get(0).shopId, 1)

            }
        }
    }

    override fun onMaintainList(pagemodel: PageModel<MaintainListBean>) {
        super.onMaintainList(pagemodel)
        pagemodel.let {
            LogUtil.i("维修部件里的信息=========$it")
            it.data?.let { data ->
                maintainClassifyList.clear()
                maintainClassifyList.addAll(data.list)
                adapter.notifyDataSetChanged()
                data.list.let { list ->
                    goodsList.clear()
                    goodsList.addAll(list.get(0).goodsList)
                    partsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

}