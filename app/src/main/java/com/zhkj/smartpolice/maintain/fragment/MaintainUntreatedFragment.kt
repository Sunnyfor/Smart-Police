package com.zhkj.smartpolice.maintain.fragment

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.maintain.activity.AuditInfoActivity
import com.zhkj.smartpolice.maintain.adapter.MaintainAuditListAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainAuditBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.frag_untreated.*


class MaintainUntreatedFragment : BaseFragment(), IMaintainView {
    private val maintainAuditList = ArrayList<MaintainAuditBean>()
    private val pullRefreshFragment = PullRefreshFragment<MaintainAuditBean>()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainAuditListAdapter by lazy {
        MaintainAuditListAdapter(maintainAuditList, true).apply {
            setOnItemClickListener { _, position ->
                var intent = Intent(requireContext(), AuditInfoActivity::class.java)
                intent.putExtra("applyId", getData(position).applyId)
                intent.putExtra("petitioner", getData(position).petitioner)
                intent.putExtra("petitionerPhone", getData(position).petitionerPhone)
                intent.putExtra("deptName", getData(position).deptName)
                intent.putExtra("applyDate", getDate(getData(position).applyDate))
                intent.putExtra("applyContent", getData(position).applyContent)
                intent.putExtra("isType", false)
                intent.putExtra("groupId",getData(position).attachmentGroupId)
                startActivityForResult(intent, Constant.MAINTAIN_CONTENT_ANSWER)
            }
        }
    }

    override fun setLayout(): Int = R.layout.frag_untreated

    override fun initView() {
        pullRefreshFragment.layoutManager = LinearLayoutManager(requireContext())
        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        childFragmentManager.beginTransaction().replace(fl_maintain_list.id, pullRefreshFragment).commit()
    }

    override fun onClickEvent(view: View) {
    }

    override fun loadData() {
        maintainPresenter.onMaintainAudit(Constant.MAINTAIN_ESTATE, pullRefreshFragment.page.toString())
    }

    override fun close() {

    }

    override fun onMaintainAudit(pagemodel: ArrayList<MaintainAuditBean>) {
        pullRefreshFragment.addData(pagemodel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Constant.MAINTAIN_CONTENT_ANSWER -> {
                if (resultCode == Activity.RESULT_OK) {
                    loadData()
                }
            }
        }
    }
}