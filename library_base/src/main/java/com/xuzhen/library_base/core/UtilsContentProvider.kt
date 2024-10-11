package com.xuzhen.library_base.core

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri


/**
 *
 * 自启动、获取Application的工具类
 * @author TXZ
 * @version 1.0
 * created by 2024/5/17 10:50
 *
 *     <application>
 *         <provider
 *             android:name=".core.UtilsContentProvider"
 *             android:authorities="com.utils"
 *             android:exported="false"
 *             android:grantUriPermissions="false" />
 *     </application>
 *
 */
internal class UtilsContentProvider : ContentProvider() {
    companion object {
        lateinit var application: Application
    }

    override fun onCreate(): Boolean {
        application = context as Application
//        Logger.initLogUtils(BuildConfig.DEBUG)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }
}