package com.example.pppetrv.testapplication.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.os.Handler
import android.provider.Settings
import android.os.Build
import android.os.Bundle
import com.example.pppetrv.testapplication.App

object ActivityUtil {

    private const val CAN_DRAW_OVERLAYS_REQUEST_CODE = 4325

    private fun scheduleOnMainThread(r: Runnable) {
        Handler(Looper.getMainLooper()).post(r)
    }

    fun scheduleOnMainThread(r: Runnable, delay: Long) {
        Handler(Looper.getMainLooper()).postDelayed(r, delay)
    }

    fun runOnMainThread(r: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run()
        } else {
            scheduleOnMainThread(r)
        }
    }

    fun dumpIntent(i: Intent?): String {
        if (i == null) {
            return ""
        }
        return dumpBundle(i.extras)
    }

    fun dumpBundle(bundle: Bundle?): String {
        if (bundle == null) {
            return ""
        }
        val s = StringBuilder()
        if (bundle != null) {
            val keys = bundle.keySet()
            val it = keys.iterator()
            while (it.hasNext()) {
                val key = it.next()
                s.append("[" + key + "= " + bundle.get(key) + "]").append("\n")
            }
        }
        return s.toString()
    }

    fun canDrawOverlays(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }

    fun checkCanDrawOverlays(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canDrawOverlays(activity)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity.packageName))
                activity.startActivityForResult(intent, CAN_DRAW_OVERLAYS_REQUEST_CODE)
            }
        }
    }

    fun getPackageName(): String {
        return (App.appInstance ?: App.appInstance?.packageName) as String
    }
}