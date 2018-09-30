package com.dawoo.gamebox.adapter.HomeAdapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.payInfo.DepositBean;
import com.dawoo.gamebox.bean.payInfo.PayItemBean;
import com.dawoo.gamebox.bean.payInfo.PayTypeBean;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.util.NetUtil;

import java.util.Collection;

/**
 * 支付大分类adapter
 *
 * @author rain
 * @date 18-3-23
 */

public class DepostTypeAdapter extends BaseQuickAdapter {
    private int seletedIndex = -1;
    RequestOptions options = new RequestOptions().placeholder(R.mipmap.default_pay);

    public DepostTypeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        String itemName = "";
        int position = mData.indexOf(item);
        if (item instanceof DepositBean) {
            itemName = ((DepositBean) item).getName();
            String iconUrl = NetUtil.handleUrl(((DepositBean) item).getIconUrl());
            GlideApp.with(BoxApplication.getContext()).load(iconUrl).apply(options).into((ImageView) helper.getView(R.id.item_icon));
            if (position == seletedIndex) {
                helper.itemView.setSelected(true);
                helper.setTextColor(R.id.item_name, BoxApplication.getContext().getResources().getColor(R.color.colorPrimary));
            } else {
                helper.itemView.setSelected(false);
                helper.setTextColor(R.id.item_name, BoxApplication.getContext().getResources().getColor(R.color.text_color_gray_333333));
            }
        } else if (item instanceof Double || item instanceof String) {
            itemName = String.valueOf(item);
            switch (position) {
                case 0:
                    helper.setBackgroundRes(R.id.item_name, R.mipmap.money_01);
                    break;
                case 1:
                    helper.setBackgroundRes(R.id.item_name, R.mipmap.money_02);
                    break;
                case 2:
                    helper.setBackgroundRes(R.id.item_name, R.mipmap.money_03);
                    break;
                case 3:
                    helper.setBackgroundRes(R.id.item_name, R.mipmap.money_04);
                    break;
                default:
                    helper.setBackgroundRes(R.id.item_name, R.mipmap.money_05);
                    break;
            }

        } else {
            itemName = ((PayItemBean) item).getAliasName();
            if (itemName == null || itemName.isEmpty()) {
                itemName = ((PayItemBean) item).getPayName();
            }
            String url = NetUtil.handleUrl(((PayItemBean) item).getImgUrl());
            GlideApp.with(BoxApplication.getContext()).load(url).apply(options).into((ImageView) helper.getView(R.id.item_icon));
            if (position == seletedIndex) {
                helper.itemView.setSelected(true);
            } else {
                helper.itemView.setSelected(false);
            }
        }
        if (itemName == null) {
            itemName = "test is null";
        }
        helper.setText(R.id.item_name, itemName);
    }

    public void setSeletedIndex(int seletedIndex) {
        this.seletedIndex = seletedIndex;
        notifyDataSetChanged();
    }

    @Override
    public void replaceData(@NonNull Collection data) {
        this.seletedIndex = 0;
        super.replaceData(data);
    }
}
