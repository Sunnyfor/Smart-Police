package com.zhkj.smartpolice.maintain.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.adapter.MaintainAccompListAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainAccompListBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.frag_processed.*

class ProcessedFragment : BaseFragment(), IMaintainView {
    private val pullRefreshFragment = PullRefreshFragment<MaintainAccompListBean>()
    private val maintainAccompListBean = ArrayList<MaintainAccompListBean>()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainAccompListAdapter by lazy {
        MaintainAccompListAdapter(maintainAccompListBean,true)
    }

    override fun setLayout(): Int = R.layout.frag_processed

    override fun initView() {
        pullRefreshFragment.layoutManager = LinearLayoutManager(requireContext())
        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        childFragmentManager.beginTransaction().replace(fl_processed_list.id, pullRefreshFragment)
            .commit()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        maintainPresenter.onMaintainAccomplish(pullRefreshFragment.page.toString())
    }

    override fun close() {

    }

    override fun onMaintainAccomplish(info: ArrayList<MaintainAccompListBean>) {
        LogUtil.i("已审核完成的数据======$info")
        pullRefreshFragment.addData(info)
    }
}