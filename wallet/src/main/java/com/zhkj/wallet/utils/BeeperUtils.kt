package com.zhkj.wallet.utils

import android.content.Context
import android.media.SoundPool
import com.zhkj.wallet.R

/**
 * Desc
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/8/17 22:38
 */
object BeeperUtils {

    private const val BEEPER_SHORT = 1

    private val successSoundPool: SoundPool by lazy {
        SoundPool.Builder().setMaxStreams(1)
            .build()
    }

    private val filedSoundPool: SoundPool by lazy {
        SoundPool.Builder().setMaxStreams(1)
            .build()
    }

    fun init(context: Context) {
        successSoundPool.load(context.applicationContext, R.raw.sacn_success, BEEPER_SHORT)
        filedSoundPool.load(context.applicationContext, R.raw.sacn_failed, BEEPER_SHORT)
    }

    fun beepSuccess() {
        successSoundPool.play(BEEPER_SHORT, 1f, 1f, 0, 0, 1f)

    }

    fun beepFailed() {
        filedSoundPool.play(BEEPER_SHORT, 1f, 1f, 0, 0, 1f)
    }

    fun release() {
        successSoundPool.release()
        filedSoundPool.release()
    }
}