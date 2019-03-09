package dev.anhndt.gobear.utils;

import android.content.Context;
import android.content.Intent;

public class IntentUtils {
    public static void startActivity(Context ctx, Class clazz) {
        ctx.startActivity(new Intent(ctx, clazz));
    }
}
