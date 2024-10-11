package com.xuzhen.library_base.utils

import android.os.Build
import android.util.Log
import com.example.library_base.core.CoreUtils
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * 进行错误信息处理与保存，保存日期30天，存放路径为 外部私有目录下的，crash文件夹中
 * @version 1.0
 * @date 2024/7/22 10:17
 */
object CrashHandlerUtils : Thread.UncaughtExceptionHandler {
    private const val TAG = "CrashHandlerUtils"
    private const val CRASH = "crash"
    private val crashDir = CoreUtils.getApp().getExternalFilesDir(CRASH)
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    private val fileFormat = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
    private val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    fun init() {
        Thread.setDefaultUncaughtExceptionHandler(this)
        clearFile()
    }

    override fun uncaughtException(thread: Thread, e: Throwable) {
        val filename = generateFileName()
        val file = File(crashDir, filename)
        //如果crash文件不存在，就创建crash 文件夹，无需要FileProvider进行分享
        if (!file.parentFile!!.exists()) file.mkdir()
        val crashInfo = createCrash(thread, e)
        val fileOutputStream = FileOutputStream(file, true)
        val outputStreamWriter = OutputStreamWriter(fileOutputStream, Charset.defaultCharset())
        val bufferedWriter = BufferedWriter(outputStreamWriter).buffered()
        val printWriter = PrintWriter(bufferedWriter)
        printWriter.use {
            it.println(crashInfo)
        }
        printWriter.close()
        defaultHandler?.uncaughtException(thread, e)
    }

    private fun createCrash(thread: Thread, e: Throwable): String {
        val sb = StringBuilder()
        sb.appendLine("错误时间： ${timeFormat.format(System.currentTimeMillis())}")
        sb.appendLine("设备信息：${Build.BRAND}")
        sb.appendLine("设备型号：${Build.MODEL}")
        sb.appendLine("品牌名称：${Build.PRODUCT}")
        sb.appendLine("SDK版本：${Build.VERSION.SDK_INT}")
        sb.appendLine("Android版本：${Build.VERSION.RELEASE}")
        sb.appendLine("----------------------")
        sb.appendLine("错误线程Thread：${thread.name}")
        sb.appendLine("错误线程：${e.message}")
        sb.appendLine("错误堆栈：${e.stackTraceToString()}")
        Log.e(TAG, "程序崩溃:$sb")
        return sb.toString()
    }

    private fun generateFileName(): String {
        return "crash-${fileFormat.format(System.currentTimeMillis())}.txt"
    }

    /**
     * 超过30天的文件进行删除操作
     */
    private fun clearFile(day: Int = -30) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, day)
        if (crashDir != null && crashDir.exists()) {
            val files = crashDir.listFiles()
            if (files != null) {
                for (f in files) {
                    val lastModified = f.lastModified()
                    timeFormat.format(calendar.time)
                    if (lastModified < calendar.timeInMillis) {
                        f.delete()
                    }
                }
            }
        }
    }
}

