package com.dawoo.coretool.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

/**
 * SharedPreferences 工具
 * Create by Fei on 16-12-08.
 */
public class SPTool {
    /**
     * 保存在手机里面的文件名
     */
    private static final String SHARE_NAME = "share_data";

    /**
     * 保存数据
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }

        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    /**
     * 度取数据
     */
    public static Object get(Context context, String key, Object defValue) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);

        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }

        return null;
    }

    /**
     * 删除数据
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (key != null) {
            editor.remove(key);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    /**
     * 清空数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }
}