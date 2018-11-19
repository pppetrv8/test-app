package com.example.pppetrv.testapplication.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothHeadset

object BluetoothUtil {

    const val STATE_CHANGED = BluetoothAdapter.ACTION_STATE_CHANGED
    const val BT_STATE = BluetoothAdapter.EXTRA_CONNECTION_STATE
    const val BT_DISCONNECTED = BluetoothAdapter.STATE_DISCONNECTED
    const val HEADSET_STATE_CHANGED = BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED
    const val HEADSET_STATE = BluetoothHeadset.EXTRA_STATE
    const val HEADSET_CONNECTED = BluetoothHeadset.STATE_CONNECTED
    const val HEADSET_DISCONNECTED = BluetoothHeadset.STATE_DISCONNECTED
    const val HEADSET = BluetoothHeadset.HEADSET

    private fun isBluetoothSupported(): Boolean =
            BluetoothAdapter.getDefaultAdapter() != null

    private fun isBluetoothConnected(): Boolean {
        return if (isBluetoothSupported()) {
            BluetoothAdapter.getDefaultAdapter().isEnabled
        } else {
            false
        }
    }

    fun isBluetoothAvailable(): Boolean = isBluetoothSupported()
            && isBluetoothConnected()

    fun isHeadsetBluetoothConnected(): Boolean {
        return if (isBluetoothSupported() && isBluetoothConnected()) {
            BluetoothAdapter.getDefaultAdapter()
                    .getProfileConnectionState(HEADSET) == HEADSET_CONNECTED
        } else {
            false
        }
    }

}