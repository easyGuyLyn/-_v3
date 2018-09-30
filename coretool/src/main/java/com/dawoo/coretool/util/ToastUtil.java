package com.dawoo.coretool.util;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;


/**
 * Created by benson on 17-12-28.
 */

public class ToastUtil {
    public static void showResShort(Context context, @StringRes int stringId) {
        if (!commonCheck(context)) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
        } else {
            Looper.prepare();
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static void showResLong(Context context, @StringRes int stringId) {
        if (!commonCheck(context)) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
        } else {
            Looper.prepare();
            Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }

    public static void showToastShort(Context context, String msg) {
        if (!commonCheck(context)) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

    }

    public static void showToastLong(Context context, String msg) {
        if (!commonCheck(context)) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }

    private static boolean commonCheck(Context context) {
        if (context == null) {
            return false;

        }
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return false;
            }
            if (((Activity) context).isDestroyed()) {
                return false;
            }
        }
        return true;
    }


}
