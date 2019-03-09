package dev.anhndt.gobear;

import android.app.Application;

public class GoBearApp extends Application {
    private static GoBearApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized GoBearApp getInstance() {
        return mInstance;
    }
}
