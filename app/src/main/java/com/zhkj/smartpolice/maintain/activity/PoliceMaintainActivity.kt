package com.zhkj.smartpolice.maintain.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.PageModel
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.adapter.MaintainClassifyAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainClassifyBean
import com.zhkj.smartpolice.maintain.bean.MaintainListBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.act_police_maintain.*

class PoliceMaintainActivity : BaseActivity(), IMaintainView {
    var maintainClassifyList: ArrayList<MaintainListBean> = ArrayList()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val adapter: MaintainClassifyAdapter by lazy {
        MaintainClassifyAdapter(maintainClassifyList,PoliceMaintainActivity@this).apply {
            setOnItemClickListener {view, position ->
                LogUtil.i("点击了这条Item的数据=========${getData(position).goodsList}")
//                onclicklistener.setOnclicklistener(position)
            }
        }
    }


    override fun setLayout(): Int = R.layout.act_police_maintain

    override fun initView() {
        defaultTitle("维修部件")
        rv_left_navigation.layoutManager = layoutManager
        rv_left_navigation.adapter = adapter
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
        pageModel?.let {
            it.data?.let { data ->
                maintainPresenter.onMaintainList(data.list.get(0).shopId, 1)

            }
        }
    }

    override fun onMaintainList(pagemodel: PageModel<MaintainListBean>) {
        super.onMaintainList(pagemodel)
        pagemodel?.let {
            LogUtil.i("维修部件里的信息=========$it")
            it.data?.let { data ->
                maintainClassifyList.clear()
                maintainClassifyList.addAll(data.list)
                adapter.notifyDataSetChanged()
            }
        }
    }



//    lateinit var onclicklistener: onClicklistener
//
//
//    interface onClicklistener {
//        fun setOnclicklistener(position: Int){}
//    }
//
//
//    fun setOnClicklistener(onClicklistener: onClicklistener){
//        this.onclicklistener = onclicklistener
//    }


}