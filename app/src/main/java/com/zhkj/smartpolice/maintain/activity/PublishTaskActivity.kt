package com.zhkj.smartpolice.maintain.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.SpUtil
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.widget.dialog.PutInSucceedDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.adapter.MaintainerListAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainerListBean
import com.zhkj.smartpolice.maintain.bean.SucceedBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.act_publish_task.*

class PublishTaskActivity : BaseActivity(), IMaintainView {
    val info = ArrayList<MaintainerListBean>()
    var nickName: String? = null
    var userId: String? = null
    var operationPhone: String? = null

    companion object {

        fun intent(
            context: Context,
            applyContent: String?,
            applyId: String?,
            repairDate: String?
        ) {
            val intent = Intent(context, PublishTaskActivity::class.java)
            intent.putExtra("applyContent", applyContent)
            intent.putExtra("applyId", applyId)
            intent.putExtra("repairDate", repairDate)
            context.startActivity(intent)
        }
    }

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val putInSucceedDialog: PutInSucceedDialog by lazy {
        PutInSucceedDialog(this)
    }

    private val adapter: MaintainerListAdapter by lazy {
        MaintainerListAdapter(info).apply {
            setOnItemClickListener { _, position ->
                if (SpUtil.getString("last").isNotEmpty()) {
                    getData(SpUtil.getString("last").toInt()).isCheck = false
                }
                getData(position).isCheck = true
                SpUtil.setString("last", position.toString())
                notifyDataSetChanged()
                nickName = getData(position).nickName
                userId = getData(position).userId
                operationPhone = getData(position).mobile
            }
        }
    }

    override fun setLayout(): Int = R.layout.act_publish_task

    override fun initView() {
        defaultTitle("下发 ")
        rv_barber_list.layoutManager = LinearLayoutManager(this)
        rv_barber_list.adapter = adapter
        tv_return.setOnClickListener(this)
        tv_send.setOnClickListener(this)
        intent.getStringExtra("applyContent")
        intent.getStringExtra("applyId")
        intent.getStringExtra("repairDate")
        LogUtil.i(
            "传过来的下发参数========${intent.getStringExtra("applyContent") + intent.getStringExtra("applyId") +
                    intent.getStringExtra("repairDate")}"
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.tv_return -> finish()

            R.id.tv_send -> {
                nickName?.let { nickName ->
                    userId?.let { userId ->
                        operationPhone?.let { operationPhone ->
                            maintainPresenter.onIssueTask(
                                intent.getStringExtra("applyContent"),
                                nickName,
                                userId,
                                operationPhone,
                                intent.getStringExtra("applyId"),
                                intent.getStringExtra("repairDate")
                            )
                        }
                    }
                }

            }
        }
    }

    override fun loadData() {
        maintainPresenter.onMaintainerList("46", "1", "1", "4")
    }

    override fun close() {

    }

    override fun onMaintainerList(barberListBean: ArrayList<MaintainerListBean>) {
        barberListBean.let {
            LogUtil.i("维修工人列表=========$barberListBean")
            info.clear()
            info.addAll(barberListBean)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onIssueTask(succeedBean: SucceedBean) {
        succeedBean.let {
            putInSucceedDialog.show()
            putInSucceedDialog.onServiceListener = {
                putInSucceedDialog.dismiss()
                ToastUtil.show("下发成功")
                ZyFrameStore.setData("isGoData", true)
                finish()
            }
        }
    }
}