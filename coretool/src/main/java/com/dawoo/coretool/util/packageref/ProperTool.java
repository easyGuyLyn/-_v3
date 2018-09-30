package com.dawoo.coretool.util.packageref;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class ProperTool {

    private static Properties getProperties(Context context) {
        Properties props = new Properties();
        try {
            InputStream in = context.getAssets().open("conf/conf.properties");
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    public static String getProperty(Context context, String confName) {
        Properties properties = getProperties(context);
        return properties.getProperty(confName);
    }

}