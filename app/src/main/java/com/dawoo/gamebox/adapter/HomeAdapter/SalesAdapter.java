package com.dawoo.gamebox.adapter.HomeAdapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.payInfo.SaleBean;

/**
 *
 * @author rain
 * @date 18-3-25
 */

public class SalesAdapter extends BaseQuickAdapter {
    private int selectedIndex;

    public SalesAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        SaleBean bean = (SaleBean) item;
        helper.setText(R.id.sale_name, bean.getActivityName());
        int position = mData.indexOf(item);
        if (selectedIndex == position) {
            helper.getView(R.id.selected_iv).setSelected(true);
        } else {
            helper.getView(R.id.selected_iv).setSelected(false);
        }

//        if(position==mData.size()-1){
//            helper.getView(R.id.bottom_line).setVisibility(View.GONE);
//        }else{
//            helper.getView(R.id.bottom_line).setVisibility(View.VISIBLE);
//        }
    }


    public void setSelected(int selectedIndex) {
        if (selectedIndex == this.selectedIndex) {
            return;
        }
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }
}
