package com.zhkj.smartpolice.maintain.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.base.PageModel
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

    override fun setLayout(): Int = R.layout.frag_untreated

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainAuditListAdapter by lazy {
        MaintainAuditListAdapter(maintainauditList,false).apply {
            setOnItemClickListener { _, position ->
                LogUtil.i("点击了这条item的数据==========${getData(position)}")
                AuditInfoActivity.intent(
                    requireContext(),
                    getData(position).applyId,
                    getData(position).petitioner,
                    getData(position).petitionerPhone,
                    getData(position).deptName,
                    getData(position).applyDate,
                    getData(position).applyContent
                )
            }
        }
    }

    override fun initView() {
        rv_maintain_list.layoutManager = linearLayoutManager
        rv_maintain_list.adapter = adapter
        LogUtil.i("我进入了这个页面")
    }

    override fun onClickEvent(view: View) {
    }

    override fun loadData() {
        maintainPresenter.onMaintainAudit(Constant.MAINTAIN_AUDIT)
    }

    override fun close() {
    }

    override fun onMaintainAudit(baseModel: PageModel<MaintainAuditBean>) {
        super.onMaintainAudit(baseModel)
        baseModel?.let {
            LogUtil.i("下载的审批任务是============${baseModel}}")
            it.data?.let { info ->
                maintainauditList.clear()
                maintainauditList.addAll(info.list)
                adapter.notifyDataSetChanged()
            }
        }
    }
}