package com.zhkj.smartpolice.maintain.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.maintain.activity.AuditInfoActivity
import com.zhkj.smartpolice.maintain.adapter.MaintainAuditListAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainAuditBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.frag_untreated.*

class UntreatedFragment : BaseFragment(), IMaintainView {

    private val maintainauditList: ArrayList<MaintainAuditBean> = ArrayList()
    private val pullRefreshFragment = PullRefreshFragment<MaintainAuditBean>()

    override fun setLayout(): Int = R.layout.frag_untreated

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainAuditListAdapter by lazy {
        MaintainAuditListAdapter(maintainauditList, false).apply {
            setOnItemClickListener { _, position ->
                LogUtil.i("点击了这条item的数据==========${getData(position)}")
                val intent = Intent(requireContext(), AuditInfoActivity::class.java)
                intent.putExtra("applyId", getData(position).applyId)
                intent.putExtra("petitioner", getData(position).petitioner)
                intent.putExtra("petitionerPhone", getData(position).petitionerPhone)
                intent.putExtra("deptName", getData(position).deptName)
                intent.putExtra("applyDate", getData(position).applyDate)
                intent.putExtra("applyContent", getData(position).applyContent)
                startActivityForResult(intent, Constant.MAINTAIN_CONTENT_ANSWER)
            }
        }
    }

    override fun initView() {
        pullRefreshFragment.layoutManager = linearLayoutManager
        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        childFragmentManager.beginTransaction().replace(fl_maintain_list.id, pullRefreshFragment)
            .commit()

    }

    override fun onClickEvent(view: View) {
    }

    override fun loadData() {
        maintainPresenter.onMaintainAudit(Constant.MAINTAIN_AUDIT,pullRefreshFragment.page.toString())
    }

    override fun close() {
    }

    override fun onMaintainAudit(baseModel: ArrayList<MaintainAuditBean>) {
        pullRefreshFragment.addData(baseModel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.MAINTAIN_CONTENT_ANSWER -> {
                if (resultCode == RESULT_OK) {
                    loadData()
                }
            }
        }
    }
}