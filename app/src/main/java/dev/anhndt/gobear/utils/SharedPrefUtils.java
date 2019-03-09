package dev.anhndt.gobear.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dev.anhndt.gobear.GoBearApp;
import dev.anhndt.gobear.global.Global;

public class SharedPrefUtils {
    public static void setPreference(String key, String value) {
        try {
            SharedPreferences mPreferences = PreferenceManager
                    .getDefaultSharedPreferences(GoBearApp.getInstance().getApplicationContext());
            SharedPreferences.Editor edit = mPreferences.edit();
            edit.putString(key, value);
            edit.apply();
        } catch (Exception e) {
        }
    }

    public static String getPreference(String key, String defaultValue) {
        try {
            SharedPreferences mPreferences = PreferenceManager
                    .getDefaultSharedPreferences(GoBearApp.getInstance().getApplicationContext());
            return mPreferences.getString(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void setRememberLogin(boolean isRemember) {
        try {
            SharedPreferences mPreferences = PreferenceManager
                    .getDefaultSharedPreferences(GoBearApp.getInstance().getApplicationContext());
            SharedPreferences.Editor edit = mPreferences.edit();
            edit.putBoolean(Global.CacheKey.REMEMBER_ME, isRemember);
            edit.apply();
        } catch (Exception e) {
        }
    }

    public static Boolean isRememberLogin() {
        try {
            SharedPreferences mPreferences = PreferenceManager
                    .getDefaultSharedPreferences(GoBearApp.getInstance().getApplicationContext());
            return mPreferences.getBoolean(Global.CacheKey.REMEMBER_ME, false);
        } catch (Exception e) {
            return false;
        }
    }
}
