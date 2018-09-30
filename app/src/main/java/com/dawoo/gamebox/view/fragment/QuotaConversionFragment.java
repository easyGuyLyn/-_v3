package com.dawoo.gamebox.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 资金转换
 */
public class QuotaConversionFragment extends BaseFragment {
    Unbinder unbinder;


    public static QuotaConversionFragment newInstance() {
        QuotaConversionFragment mQuotaConversionFragment = new QuotaConversionFragment();
        return mQuotaConversionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quota_conversion, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public String toString() {
        String title="资金转换";
        return title;
    }

    @Subscribe(tags = {@Tag(ConstantValue.QUOTA_CONVERSION_FRAGMENT_LOAD)})
    public void goGetDate(String s) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.e("TT","进来了3333");
        }else {
            Log.e("TT","离开了3333");
        }
    }
}
