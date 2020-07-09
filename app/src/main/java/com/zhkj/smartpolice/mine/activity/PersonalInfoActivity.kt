package com.zhkj.smartpolice.mine.activity

import android.view.View
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.isStrEmpty
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.mine.bean.UserBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import kotlinx.android.synthetic.main.act_personal_info.*
import kotlinx.coroutines.cancel


/**
 * 个人信息页
 */
class PersonalInfoActivity : BaseActivity(), UserContract.IUserInfoView {

    private val presenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    override fun setLayout(): Int = R.layout.act_personal_info

    override fun initView() {

        defaultTitle("个人信息")

    }

    override fun onClickEvent(v: View) {

    }

    override fun loadData() {
        presenter.loadUserInfo()
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showUserInfo(data: UserBean) {

        UserManager.setUserBean(data)

        Glide.with(this)
            .load("${UrlConstant.IMAGE_PATH_URL}${data.avatar}")
            .placeholder(R.drawable.svg_default_head)
            .into(iv_head)

        item_name.endTextView.text = isStrEmpty(data.userName)
        item_nickName.endTextView.text = isStrEmpty(data.nickName)
        item_phone.endTextView.text = isStrEmpty(data.mobile)
        item_email.endTextView.text = isStrEmpty(data.email)
        item_sign.endTextView.text = isStrEmpty(data.sign)
    }
}