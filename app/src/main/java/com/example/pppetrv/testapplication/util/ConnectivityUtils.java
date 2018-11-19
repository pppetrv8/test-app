package com.example.pppetrv.testapplication.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pppetrv.testapplication.App;

public class ConnectivityUtils {

    private ConnectivityListener connectListener;
    private ConnectivityReceiver connectReceiver;
    private boolean enabled = true;
    private volatile boolean netConnected = false;

    public ConnectivityUtils(Context context, ConnectivityListener connectListener) {
        this.connectListener = connectListener;
        netConnected = isNetworkConnected(context);
    }

    public void registerConnectReceiver(Activity activity) {
        unregisterConnectReceiver(activity);
        connectReceiver = new ConnectivityReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(connectReceiver, intentFilter);
    }

    public void unregisterConnectReceiver(Activity activity) {
        if (connectReceiver != null) {
            activity.unregisterReceiver(connectReceiver);
            connectReceiver = null;
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static boolean isNetworkConnected(Context context) {
        return getConnectedNetworkInfo(context) != null;
    }

    public static boolean isNetworkConnected() {
        return getConnectedNetworkInfo(App.Companion.getAppInstance()) != null;
    }

    private static NetworkInfo getConnectedNetworkInfo(Context context) {
        NetworkInfo ifConnected = null;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo connection : info) {
                if (connection.getState().equals(NetworkInfo.State.CONNECTED)) {
                    ifConnected = connection;
                    break;
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return ifConnected;
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getConnectedNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public class ConnectivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean connected = isNetworkConnected(context);
            if (enabled && connectListener != null) {
                if (netConnected != connected) {
                    connectListener.networkConnectionChanged(connected);
                }
            }
            netConnected = connected;
        }
    }

    public interface ConnectivityListener {
        void networkConnectionChanged(boolean connected);
    }
}
