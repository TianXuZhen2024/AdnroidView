package com.xuzhen.library_base.core

import android.app.Application
import com.xuzhen.library_base.utils.CrashHandlerUtils

/**
 * @version 1.0
 * @date 2024/7/22 15:24
 */
class CoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initCrashHandler()
    }

    private fun initCrashHandler() {
        CrashHandlerUtils.init()
    }
}