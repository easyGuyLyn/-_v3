package com.dawoo.pushsdk.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetBroadcastReceiver() {
    }

    private NetContentListener netContentListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            if (netContentListener != null)
                netContentListener.netContent(false);
        } else {
            if (netContentListener != null)
                netContentListener.netContent(true);
        }
    }

    public void setNetContentListener(NetContentListener netContentListener) {
        this.netContentListener = netContentListener;
    }

    public interface NetContentListener {
        void netContent(boolean isConnected);
    }
}
