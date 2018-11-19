package com.example.pppetrv.testapplication.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import timber.log.Timber;

public class PhoneTools {

    private static String getITelephonyStr = "getITelephony";
    private static String endCallStr = "endCall";

    public static boolean endCall(Context context) {
        try {
            // Get the boring old TelephonyManager
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            // Get the getITelephony() method
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod(getITelephonyStr);

            // Ignore that the method is supposed to be private
            methodGetITelephony.setAccessible(true);

            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass =
                    Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod(endCallStr);

            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);

        } catch (Exception e) { // Many things can go wrong with reflection calls
            Timber.e(e);
            return false;
        }
        return true;
    }
}
