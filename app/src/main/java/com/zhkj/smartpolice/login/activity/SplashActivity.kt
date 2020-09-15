package com.zhkj.smartpolice.login.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_splash.*
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)

        //判断是debug模式直接跳转至登录页
//        if (isApkInDebug(this)) {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//            return
//        }
        showAppNameAnim()
    }

    private fun showAppNameAnim() {

        val nameObjectAnimator = ObjectAnimator.ofFloat(ll_app_name, "alpha", 0f, 1f)
        val scaleX2 = ObjectAnimator.ofFloat(ll_app_name, "scaleX", 0f, 1f)
        val scaleY2 = ObjectAnimator.ofFloat(ll_app_name, "scaleY", 0f, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.play(nameObjectAnimator).with(scaleX2).with(scaleY2)
        animatorSet.duration = 1000
        animatorSet.start()

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationEnd(animator: Animator) {
                iv_logo.visibility = View.VISIBLE
                showAppLogoAnim()
            }

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    private fun showAppLogoAnim() {

        val logoObjectAnimator = ObjectAnimator.ofFloat(iv_logo, "alpha", 0f, 1f)
        val scaleX1 = ObjectAnimator.ofFloat(iv_logo, "scaleX", 0f, 1f)
        val scaleY1 = ObjectAnimator.ofFloat(iv_logo, "scaleY", 0f, 1f)

        val animatorSet1 = AnimatorSet()
        animatorSet1.play(logoObjectAnimator).with(scaleX1).with(scaleY1)
        animatorSet1.duration = 1000
        animatorSet1.start()
        animatorSet1.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationEnd(animator: Animator) {
                sleep(500)
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })
    }


}