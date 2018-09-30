package com.dawoo.pushsdk.util;

public class CommonUtils {

    public static String cutUrl(String url) {
        String head;
        String body;
        if (url.startsWith("https")) {
            head = "wss";
            body = url.substring(5, url.length());
        } else {
            head = "ws";
            body = url.substring(4, url.length());
        }
        return head + body;
    }
}
