package com.zhkj.smartpolice.drugstore

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.drugstore.model.DrugstoreContract
import com.zhkj.smartpolice.drugstore.model.DrugstorePresenter
import kotlinx.android.synthetic.main.act_feedback.*
import kotlinx.coroutines.cancel

class FeedbackActivity : BaseActivity(), DrugstoreContract.IFeedbackView {

    private var shopId: String? = null

    private val presenter: DrugstorePresenter by lazy {
        DrugstorePresenter(this)
    }

    companion object {
        fun intent(context: Context, shopId: String?) {
            val intent = Intent(context, FeedbackActivity::class.java)
            intent.putExtra("shopId", shopId)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_feedback

    override fun initView() {
        defaultTitle("意见箱")

        shopId = intent.getStringExtra("shopId")

        setOnClickListener(btn_commit)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_commit.id -> {
                val feedback = et_input.text.toString()
                if (feedback.isEmpty()) {
                    ToastUtil.show("请输入意见反馈")
                    return
                }
                val phone = et_phone.text.toString()
                if (phone.isEmpty()) {
                    ToastUtil.show("请输入联系方式")
                    return
                }
                presenter.commitFeedback(shopId ?: return, feedback, phone)
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {
        presenter.cancel()
    }

    override fun commitFeedback(data: String) {
        if (data == "success") {
            ToastUtil.show("意见反馈成功")
            finish()
        } else {
            ToastUtil.show(data)
        }
    }

}