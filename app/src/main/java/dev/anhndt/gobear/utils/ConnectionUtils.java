package dev.anhndt.gobear.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import dev.anhndt.gobear.GoBearApp;

public class ConnectionUtils {
    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @return
     */
    public static boolean isConnected() {
        try {
            NetworkInfo info = getNetworkInfo(GoBearApp.getInstance().getApplicationContext());
            return (info != null && info.isConnected());
        } catch (Exception e) {
            return true;
        }
    }

}