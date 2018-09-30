package com.dawoo.gamebox.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.ArrayList;
import java.util.List;

public class CapitalViewPagerFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {


    public static final int TAB_INDEX_DEPOSIT = 0;
    public static final int TAB_INDEX_QUOTA_CONVERSION = 1;
    public static final int TAB_INDEX_WITHDRAW_MONEY = 2;

    private String[] title = {"存款", "资金转换", "取款"};
    private int mViewPagerNum = 0;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    public static CapitalViewPagerFragment newInstance() {
        CapitalViewPagerFragment mQCapitalViewPagerFragment = new CapitalViewPagerFragment();
        return mQCapitalViewPagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_capital, container, false);
        mViewPager = rootView.findViewById(R.id.view_pager);
        mTabLayout = rootView.findViewById(R.id.tablayout);
        initView();
        initData();
        return rootView;
    }

    @Override
    protected void loadData() {

    }

    private void initView() {
        RxBus.get().register(this);
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(DepositHomeFragment.newInstance());
        fragmentList.add(QuotaConversionFragment.newInstance());
        fragmentList.add(WithdrawMoneyFragment.newInstance());
        AdapterFragment adpter = new AdapterFragment(fragmentManager, fragmentList);
        mViewPager.setAdapter(adpter);
        // 预加载数量
        mViewPager.setOffscreenPageLimit(3);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(this);

        for (int i = 0; i < adpter.getCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.tab_item);//给每一个tab设置view
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
            textView.setText(title[i]);//设置tab上的文字
        }
        mViewPagerNum = 0;
    }

    private void initData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
        mViewPagerNum = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    class AdapterFragment extends FragmentPagerAdapter {
        List<Fragment> mFragments = new ArrayList<>();

        public AdapterFragment(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments.clear();
            mFragments.addAll(fragments);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }


//        @Override
//        public CharSequence getPageTitle(int position) {
//            String title = mFragments.get(position).toString();
//            return title.toString();
//        }
    }


    /**
     * 切换页面
     * 连带改变tab选中状态
     *
     * @param index tab下标
     */
    public void switchTab(int index) {
        mViewPagerNum = index;
        switch (index) {
            case TAB_INDEX_DEPOSIT:
                mViewPager.setCurrentItem(TAB_INDEX_DEPOSIT, false);
                //  RxBus.get().post(ConstantValue.DEPOSIT_CLICK_LISTENER, "onClickDeposit");
                break;
            case TAB_INDEX_QUOTA_CONVERSION:
                mViewPager.setCurrentItem(TAB_INDEX_QUOTA_CONVERSION, false);
                break;
            case TAB_INDEX_WITHDRAW_MONEY:
                mViewPager.setCurrentItem(TAB_INDEX_WITHDRAW_MONEY, false);
                break;
            default:
        }
    }

    public void seedRxBus(int index) {
        if (index == TAB_INDEX_WITHDRAW_MONEY) {
            RxBus.get().post(ConstantValue.WITHDRAW_MONEY_FRAGMENT_LOAD, "load");
        } else if (index == TAB_INDEX_DEPOSIT) {
            RxBus.get().post(ConstantValue.DEPOSIT_CLICK_LISTENER, "load");
        } else {
            RxBus.get().post(ConstantValue.QUOTA_CONVERSION_FRAGMENT_LOAD, "load");
        }
    }

    /**
     * 区别跳转到哪里
     */
    @Subscribe(tags = {@Tag(ConstantValue.DEPOSIT_FRAGMENT_CLICK_LISTENER)})
    public void gotoTabDeposit(Integer index) {
        switchTab(index);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            seedRxBus(mViewPagerNum);
        }
    }
}
