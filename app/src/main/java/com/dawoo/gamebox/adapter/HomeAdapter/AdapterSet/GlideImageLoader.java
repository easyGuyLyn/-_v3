package com.dawoo.gamebox.adapter.HomeAdapter.AdapterSet;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.net.GlideApp;
import com.youth.banner.loader.ImageLoader;

/**
 * 图片轮播使用的图片加载器
 * Created by benson on 17-12-25.
 */

class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.banner_default);
        GlideApp.with(context).load(path).apply(options).into(imageView);
    }
}
