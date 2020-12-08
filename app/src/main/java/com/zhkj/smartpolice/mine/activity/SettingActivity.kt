package com.zhkj.smartpolice.mine.activity

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.CameraUtil
import com.sunny.zy.utils.SpUtil
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.widget.dialog.ConfirmDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.login.activity.LoginActivity
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import com.zhkj.smartpolice.utils.SpKey
import com.zhkj.smartpolice.utils.fingerprint.FingerprintUtil
import com.zhkj.smartpolice.widget.CacheClearDialog
import com.zhkj.smartpolice.widget.CacheDataManager
import kotlinx.android.synthetic.main.act_setting.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

class SettingActivity : BaseActivity(), UserContract.IImageView {

    private var cacheSize = ""

    private val cameraUtil: CameraUtil by lazy {
        CameraUtil(this)
    }

    private val presenter: UserPresenter by lazy {
        UserPresenter(this)
    }


    private val exitDialog: ConfirmDialog by lazy {
        val dialog = ConfirmDialog(this)
        dialog.prompt = "是否确认退出应用？"
        dialog.onConfirmListener = {
            LoginActivity.intent(this, true)
        }
        dialog
    }

    private val cacheClearDialog: CacheClearDialog by lazy {
        val dialog = CacheClearDialog(this)
        dialog.cacheSize = cacheSize
        dialog.onConfirmListener = {
            CacheDataManager.clearAllCache(this)
            ToastUtil.show("缓存清理完毕")
            dialog.cacheSize = ""
            loadData()
        }
        dialog
    }

    override fun setLayout(): Int = R.layout.act_setting

    override fun initView() {

        defaultTitle("设置")

        item_name.endTextView.text = UserManager.getUserBean().userName

        /*
         v_point.visibility = if (Constant.isVersionUpdate) View.VISIBLE else View.GONE
         item_version.endTextView.text = ("V " + BuildConfig.VERSION_NAME)
         item_version.setOnClickListener(this)
         tv_service.setOnClickListener(this)
         tv_privacy.setOnClickListener(this)
         */

        val isFingerprintLogin = SpUtil.getBoolean(SpKey.isFingerprintLogin)
        swcBtn_finger.isChecked = isFingerprintLogin

        swcBtn_finger.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SpUtil.setBoolean(SpKey.isFingerprintLogin, isChecked)
            }
        }

        cameraUtil.onResultListener = object : CameraUtil.OnResultListener {
            override fun onResult(file: File) {
                presenter.uploadImage(UrlConstant.UPLOAD_IMAGE_PATH_URL, file.path)
            }
        }


        setOnClickListener(
            item_cache,
            item_modify_pwd,
            item_fingerprint,
            item_face,
            btn_logout
        )


    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            item_cache.id -> cacheClearDialog.show()
            item_modify_pwd.id -> startActivity(Intent(this, ModifyPasswordActivity::class.java))
            item_fingerprint.id -> FingerprintUtil.intentSetting(this)
            item_face.id -> {
                cameraUtil.startCamera()
            }
            btn_logout.id -> exitDialog.show()
            /*
            R.id.item_feedback -> startActivity(Intent(this, UserFeedbackActivity::class.java))
            R.id.item_about_us -> startActivity(Intent(this, UserFeedbackActivity::class.java))
            R.id.item_version -> presenter.checkVersionUpdate(BuildConfig.VERSION_CODE)
            R.id.tv_service -> {
                startActivity(Intent(this, AgreementActivity::class.java).apply {
                    putExtra(IntentKey.agreementType, 2)
                })
            }
            R.id.tv_privacy -> {
                startActivity(Intent(this, AgreementActivity::class.java).apply {
                    putExtra(IntentKey.agreementType, 3)
                })
            }*/
        }
    }

    override fun loadData() {
        cacheSize = CacheDataManager.getTotalCacheSize(this)
        item_cache.endTextView.text = cacheSize
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraUtil.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraUtil.onActivityResult(requestCode, resultCode, data)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun uploadImage(bean: ImageBean) {
        presenter.launch {
            val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
            val params = JSONObject()
            params.put("userId", UserManager.getUserBean().userId)
            params.put("faceImg", bean.id)
            ZyHttp.postJson(UrlConstant.USER_UPDATE_FACE_URL, params.toString(), httpResultBean)
            if (httpResultBean.isSuccess() && httpResultBean.bean?.isSuccess() == true) {
                ToastUtil.show("上传人脸信息成功！")
            }
        }
    }

}