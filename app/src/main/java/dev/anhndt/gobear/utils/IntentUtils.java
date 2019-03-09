package dev.anhndt.gobear.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import dev.anhndt.gobear.R;

public class IntentUtils {
    public static void startActivity(Context ctx, Class clazz) {
        ctx.startActivity(new Intent(ctx, clazz));
    }

    public static void startActivity(Context ctx, Class clazz, Bundle extras) {
        Intent intent = new Intent(ctx, clazz);
        intent.putExtras(extras);
        ctx.startActivity(intent);
    }

    public static void shareLink(Context ctx, String link, String description) {
        if (link != null && link.trim().length() != 0 && description != null && description.trim().length() != 0) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, description);
                i.putExtra(Intent.EXTRA_TEXT, link);
                ctx.startActivity(Intent.createChooser(i, ctx.getString(R.string.gbp_common_select_app)));
            } catch (Exception e) {
                //e.toString();
            }
        }
    }
}
