package com.zhkj.smartpolice.widget

import android.os.Handler
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.TextSwitcher
import com.sunny.zy.utils.LogUtil

/**
 * Desc TextSwitcher 上下动画 / 左右动画
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/2/19 16:51
 */
class TextSwitcherAnimation(private val textSwitcher: TextSwitcher?, private var textList: List<String>, val isLeftToRight: Boolean = true) {

    private val duration = 1000
    private var delayTime = 2000

    private var marker: Int = 0

    private var inAnimationSet: AnimationSet? = null
    private var outAnimationSet: AnimationSet? = null

    private val handler = Handler()
    private val task = object : Runnable {
        override fun run() {
            nextView()
            handler.postDelayed(this, (delayTime * 2).toLong())
        }
    }

    fun start() {
        stop()
        handler.postDelayed(task, delayTime.toLong())
    }

    fun stop() {
        handler.removeCallbacks(task)
    }
/*
    fun setTexts(textList: List<String>): TextSwitcherAnimation {
        this.textList = textList
        return this
    }

    fun setDelayTime(delayTime: Int) {
        this.delayTime = delayTime
    }*/

    fun create() {
        marker = 0
        if (textSwitcher == null) {
            LogUtil.i("textSwitcher is null")
            return
        }
        textSwitcher.setText(textList[0])
        if (isLeftToRight) {
            createLeftToRightAnimation()
        } else {
            createTopToBottomAnimation()
        }
        textSwitcher.inAnimation = inAnimationSet
        textSwitcher.outAnimation = outAnimationSet
        start()
    }

    private fun createTopToBottomAnimation() {

        var height = 0
        textSwitcher?.let {
            height = it.height
            if (height <= 0) {
                it.measure(0, 0)
                height = it.measuredHeight
            }
        }

        var alphaAnimation = AlphaAnimation(0f, 1f)
        var translateAnimation = TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, height.toFloat(),
                Animation.ABSOLUTE, 0f)

        inAnimationSet = AnimationSet(true)
        outAnimationSet = AnimationSet(true)

        inAnimationSet?.addAnimation(alphaAnimation)
        inAnimationSet?.addAnimation(translateAnimation)
        inAnimationSet?.duration = duration.toLong()

        alphaAnimation = AlphaAnimation(1f, 0f)
        translateAnimation = TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, (-height).toFloat())

        outAnimationSet?.addAnimation(alphaAnimation)
        outAnimationSet?.addAnimation(translateAnimation)
        outAnimationSet?.duration = duration.toLong()
    }

    private fun createLeftToRightAnimation() {

        var width = 0
        textSwitcher?.let {
            width = it.height
            if (width <= 0) {
                it.measure(0, 0)
                width = it.measuredWidth
            }
        }

        var alphaAnimation = AlphaAnimation(0f, 1f)
        var translateAnimation = TranslateAnimation(
                Animation.ABSOLUTE, width.toFloat(),
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f)

        inAnimationSet = AnimationSet(true)
        outAnimationSet = AnimationSet(true)

        inAnimationSet?.addAnimation(alphaAnimation)
        inAnimationSet?.addAnimation(translateAnimation)
        inAnimationSet?.duration = duration.toLong()

        alphaAnimation = AlphaAnimation(1f, 0f)
        translateAnimation = TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, (-width).toFloat(),
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f)

        outAnimationSet?.addAnimation(alphaAnimation)
        outAnimationSet?.addAnimation(translateAnimation)
        outAnimationSet?.duration = duration.toLong()
    }

    private fun nextView() {
        marker = ++marker % textList.size
        textSwitcher?.setText(textList[marker])
    }

}