package com.zhkj.wallet.activity

import android.content.DialogInterface
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.SpUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.wallet.R
import com.zhkj.wallet.bean.BandCardBean
import com.zhkj.wallet.bean.PurseBean
import com.zhkj.wallet.contract.WalletContract
import com.zhkj.wallet.dialog.AddBandCardDialog
import com.zhkj.wallet.presenter.WalletPresenter
import com.zhkj.wallet.utils.MoneyInputFilter
import kotlinx.android.synthetic.main.act_withdrawal.*
import kotlinx.coroutines.cancel

@Route(path = RouterManager.WITHDRAWAL_ACTIVITY)
class WithdrawalActivity : BaseActivity(), WalletContract.IWalletView, WalletContract.IWithdrawalView, WalletContract.IBandCardView {

    val presenter: WalletContract.Presenter by lazy {
        WalletPresenter(this)
    }

    var addBandCardDialog: AddBandCardDialog? = null

    var selectBandCard: BandCardBean? = null

    var balance = 0f

    val phone: String by lazy {
        SpUtil.getString("phone")
    }

    private val bandCardList = ArrayList<BandCardBean>()

    override fun setLayout(): Int = R.layout.act_withdrawal

    override fun initView() {
        defaultTitle("提现")
        edt_money.filters = arrayOf(MoneyInputFilter())

        tv_phone.text = phone

        setOnClickListener(cl_band_card, btn_obtain, btn_commit)
    }

    override fun loadData() {
        presenter.loadPurse()
        presenter.loadBandCardList()
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            cl_band_card.id -> {
                if (bandCardList.isEmpty()) {
                    showAddDialog()
                } else {
                    showSelectDialog()
                }
            }
            btn_obtain.id -> {
                if (phone.isEmpty()) {
                    ToastUtil.show("未绑定手机号码！")
                    return
                }
                btn_obtain.isClickable = false
                btn_obtain.text = "获取中..."
                presenter.sendVerificationCode(phone)
            }
            btn_commit.id -> {
                withdrawal()
            }

        }
    }

    private fun withdrawal() {
        if (selectBandCard == null) {
            ToastUtil.show("请选择银行卡后，在进行提现！")
            return
        }

        if (edt_money.text.isEmpty()) {
            ToastUtil.show("请输入提现金额！")
            return
        }

        if (edt_money.text.toString().toFloat() > balance) {
            ToastUtil.show("余额不足，请重新输入！")
            return
        }

        if (et_verify_code.text?.length ?: 0 < 6) {
            ToastUtil.show("请输入正确的验证码！")
            return
        }
    }

    private fun showSelectDialog() {
        AlertDialog.Builder(this)
            .setTitle("选择银行卡")
            .setItems(Array(bandCardList.size) { bandCardList[it].bandCard ?: "" }) { dialog: DialogInterface, index: Int ->
                setBandCard(bandCardList[index])
                dialog.dismiss()
            }
    }


    private fun showAddDialog() {
        addBandCardDialog = AddBandCardDialog(this@WithdrawalActivity)
        addBandCardDialog?.show(presenter)
    }

    override fun close() {
        presenter.cancel()
    }

    private fun setBandCard(bean: BandCardBean) {
        selectBandCard = bean
        tv_bank_name.text = bean.bankName
        tv_card_number.text = bean.bandCard
    }

    override fun showBandCard(data: ArrayList<BandCardBean>) {
        bandCardList.clear()
        bandCardList.addAll(data)
        if (data.isNotEmpty()) {
            setBandCard(data[0])
        }
    }

    override fun showPurseData(purseBean: PurseBean) {
        balance = purseBean.balance.toFloat()
        tv_balance.text = ("当前可提现余额${balance}元")
    }

    override fun showAddBandCardResult(isSuccess: Boolean) {
        if (isSuccess) {
            addBandCardDialog?.dismiss()
            ToastUtil.show("添加成功！")
            presenter.loadBandCardList()
        } else {
            addBandCardDialog?.resetButton()
        }
    }

    override fun showVerificationCodeResult(isSuccess: Boolean) {
        btn_obtain.isClickable = true
        if (isSuccess) {
            TimeCount().start()
        }else{
            btn_obtain.text = "获取"
        }
    }

    override fun showWithdrawalResult(isSuccess: Boolean) {
        if (isSuccess) {
            finish()
            ToastUtil.show("提现成功！")
        }
    }

    /**
     * 计时器
     */
    internal inner class TimeCount : CountDownTimer(60 * 1000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            btn_obtain.text = ("( ${millisUntilFinished / 1000}) 秒重新发送")
        }

        override fun onFinish() {
            btn_obtain.text = "获取"
            btn_obtain.isClickable = true
        }
    }
}