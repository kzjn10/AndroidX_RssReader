package dev.anhndt.gobear.utils;

import android.view.Gravity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import dev.anhndt.gobear.GoBearApp;
import dev.anhndt.gobear.R;

/**
 * Created by AnhNDT on 7/12/2016.
 */

public class ToastHelper {
    private static final int DURATION = 4000;
    private static final Map<Object, Long> lastShown = new HashMap<>();

    private static boolean isRecent(Object obj) {
        Long last = lastShown.get(obj);
        if (last == null) {
            return false;
        }
        long now = System.currentTimeMillis();
        if (last + DURATION < now) {
            return false;
        }
        return true;
    }

    public static synchronized void show(int resId) {
        try {
            if (isRecent(resId)) {
                return;
            }

            Toast msg = Toast.makeText(GoBearApp.getInstance().getApplicationContext(), resId, Toast.LENGTH_LONG);
            msg.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            msg.show();
            lastShown.put(resId, System.currentTimeMillis());
        } catch (Exception e) {
        }
    }

    public static synchronized void show(String msg) {
        try {
            if (isRecent(msg)) {
                return;
            }

            Toast toast = Toast.makeText(GoBearApp.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            toast.show();
            lastShown.put(msg, System.currentTimeMillis());
        } catch (Exception e) {
        }
    }

    public static void showNetworkError() {
        show(R.string.gbp_msg_no_connection);
    }
}
