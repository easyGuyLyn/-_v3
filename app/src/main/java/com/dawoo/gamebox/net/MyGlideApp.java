package com.dawoo.gamebox.net;

import android.annotation.SuppressLint;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

@GlideExtension
public class MyGlideApp {
    // Size of mini thumb in pixels.
    private static final int MINI_THUMB_SIZE = 100;

    private MyGlideApp() {
    } // utility class

    @GlideOption
    public static void miniThumb(RequestOptions options, int size) {
        options.fitCenter().override(size);
    }

}
