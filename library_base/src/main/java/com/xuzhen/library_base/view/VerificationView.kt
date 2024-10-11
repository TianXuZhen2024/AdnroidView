package com.xuzhen.library_base.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton

/**
 * 获取短信验证码
 * @author: TXZ
 * @version: 1.0
 * @date: created by 2024/9/22 22:58
 */
class VerificationView: AppCompatToggleButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    @SuppressLint("SetTextI18n")
    override fun onFinishInflate() {
        super.onFinishInflate()
        this.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){//开启短信验证
                countDownTimer.start()
                isClickable = false
            }else{
                text="获取验证码"
            }
        }
    }

    private val countDownTimer= object :CountDownTimer(10*1000L,1000L){
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            text = "${millisUntilFinished/1000L}S"
        }

        override fun onFinish() {
            isClickable = true
            isChecked = false
        }
    }
}