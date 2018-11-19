package com.example.pppetrv.testapplication.util

import android.content.Context
import android.os.Build
import android.text.TextUtils
import java.util.*


class DeviceUtil {

    private val SHORT_ID_PREFIX = "35"

    fun getDeviceId(context: Context): String {
        return getDeviceIdFromSystem()
    }

    private fun getDeviceIdFromSystem(): String {
        return generateId()
    }

    private fun generateId(): String {
        var androidId: String? = null
        androidId = getUniquePhoneIdentity()

        if (androidId == null || TextUtils.isEmpty(androidId)) {
            return UUID.randomUUID().toString()
        }
        val deviceUuid = UUID(androidId.hashCode().toLong(), getDeviceIdShort().hashCode().toLong())
        return deviceUuid.toString()
    }

    private fun getUniquePhoneIdentity(): String? {
        val shortDeviceId = getDeviceIdShort()
        var serial: String?
        var androidId: String?
        try {
            serial = android.os.Build::class.java.getField("SERIAL").get(null).toString()
            androidId = UUID(shortDeviceId.hashCode().toLong(), serial.hashCode().toLong()).toString()
        } catch (e: Exception) {
            // String needs to be initialized
            serial = "serial" // some value
            androidId = UUID(shortDeviceId.hashCode().toLong(), serial.hashCode().toLong()).toString()
        }
        return androidId
    }

    private fun getDeviceIdShort(): String {
        return SHORT_ID_PREFIX + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 +
            Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
    }

    fun getVersionName(context: Context?): String {
        val packageInfo = context?.packageManager!!.getPackageInfo(context.packageName, Constants.INT_ZERO)
        return packageInfo.versionName
    }

    fun getDevicePlatform(context: Context?): String {
        return Build.MANUFACTURER + "/" + Build.MODEL
    }
}
