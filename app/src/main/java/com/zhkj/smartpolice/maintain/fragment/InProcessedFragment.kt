package com.zhkj.smartpolice.maintain.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.maintain.activity.MaintainTaskInfoActivity
import com.zhkj.smartpolice.maintain.adapter.MaintainTaskInfoAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainTaskBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.frag_processed.*


class InProcessedFragment : BaseFragment(), IMaintainView {
    private val maintainTaskInfo = ArrayList<MaintainTaskBean>()
    private val pullRefreshFragment = PullRefreshFragment<MaintainTaskBean>()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainTaskInfoAdapter by lazy {
        MaintainTaskInfoAdapter(maintainTaskInfo, false).apply {
            setOnItemClickListener { _, position ->
                var intent = Intent(requireContext(), MaintainTaskInfoActivity::class.java)
                var bundle = Bundle()
                bundle.putSerializable("info",getData(position))
                intent.putExtras(bundle)
                startActivityForResult(intent,Constant.MAINTAIN_CONTENT_ANSWER)
            }
        }
    }

    override fun setLayout(): Int = R.layout.frag_processed

    override fun initView() {
        pullRefreshFragment.layoutManager = LinearLayoutManager(requireContext())
        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        childFragmentManager.beginTransaction().replace(fl_processed_list.id, pullRefreshFragment).commit()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        maintainPresenter.onMaintainTask(Constant.MAINTAIN_TASK, pullRefreshFragment.page.toString())
    }

    override fun close() {
    }

    override fun onMaintainTask(maintainTaskBean: ArrayList<MaintainTaskBean>) {
        pullRefreshFragment.addData(maintainTaskBean)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Constant.MAINTAIN_CONTENT_ANSWER -> {
                if (resultCode == RESULT_OK) {
                    loadData()
                }
            }
        }
    }
}