package com.dawoo.gamebox.view.activity.message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.SiteMsgUnReadCount;
import com.dawoo.gamebox.mvp.presenter.MessagePresenter;
import com.dawoo.gamebox.mvp.view.ISiteMsgView;
import com.dawoo.gamebox.push.PushDispatchManager;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity implements ISiteMsgView {
    private final int TAB_GAME = 0;
    private final int TAB_SYS = 1;
    private final int TAB_ZHAN = 2;
    // 申请优惠
    public final static int TAB_APPLICATION_PREFERENTAIL = 3;
    public final static String WHERE_FROM = "WHERE_FROM";
    private int mType;

    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.game_notice)
    Button mGameNotice;
    @BindView(R.id.sys_notice)
    Button mSysNotice;
    @BindView(R.id.zhan_notice)
    TextView mZhanNotice;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.v_tip_my_notice)
    TextView mVTipMyNotice;

    private MessagePresenter mPresneter;
    public Fragment siteMsgFragment;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_message_center);
        RxBus.get().register(this);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.message_center_activity), true);
        mPresneter = new MessagePresenter(this, this);
    }

    @Override
    protected void initData() {
        mType = getIntent().getIntExtra(WHERE_FROM, 0);
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        titles.add(getString(R.string.game_noitce));
        titles.add(getString(R.string.sys_notice));
        titles.add(getString(R.string.zhan_message));
        fragments.add(new GameNoticeFragment());
        fragments.add(new SysNoticeFragment());
        siteMsgFragment = SiteMsgFragment.newInstance(mType);
        fragments.add(siteMsgFragment);

        OneViewPagerAdapter adapter = new OneViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        getSiteUnReadMsgCount();


        if (TAB_APPLICATION_PREFERENTAIL == mType) {
            switchTab(TAB_ZHAN);
        } else {
            switchTab(TAB_GAME);
        }

    }

    public void getSiteUnReadMsgCount() {
        mPresneter.getUnReadMsgCount();
    }

    @OnClick({R.id.game_notice, R.id.sys_notice, R.id.zhan_notice})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.game_notice:
                switchTab(TAB_GAME);
                break;
            case R.id.sys_notice:
                switchTab(TAB_SYS);
                break;
            case R.id.zhan_notice:
                switchTab(TAB_ZHAN);
                break;
        }
    }


    @Override
    public void onGetUnReadCountResult(Object o) {
        SiteMsgUnReadCount siteMsgUnReadCount = (SiteMsgUnReadCount) o;
        if (mVTipMyNotice == null) return;
        if (siteMsgUnReadCount.getSysMessageUnReadCount() > 0 || siteMsgUnReadCount.getAdvisoryUnReadCount() > 0) {
            mVTipMyNotice.setVisibility(View.VISIBLE);
//            mVTipMyNotice.setText((siteMsgUnReadCount.getSysMessageUnReadCount()
//                    + siteMsgUnReadCount.getAdvisoryUnReadCount()) + "");
        } else {
            mVTipMyNotice.setVisibility(View.GONE);
        }
    }


    public class OneViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mFragments = new ArrayList<>();
        List<String> mTitles = new ArrayList<>();

        public OneViewPagerAdapter(FragmentManager fm, List fragments, List titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(MessageCenterActivity.this).inflate(R.layout.layout_fragment_home_content_one_tab_layot_item_custom_view, null);
            TextView tvTitle = (TextView) v.findViewById(R.id.tv_tab);
            tvTitle.setText(mTitles.get(position));
            ImageView imgTab = (ImageView) v.findViewById(R.id.img_tab);
            return v;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
        }
    }


    void switchTab(int tabIndex) {
        switch (tabIndex) {
            case TAB_GAME:
                mGameNotice.setSelected(true);
                mSysNotice.setSelected(false);
                mZhanNotice.setSelected(false);
                mViewPager.setCurrentItem(TAB_GAME);
                break;
            case TAB_SYS:
                mGameNotice.setSelected(false);
                mSysNotice.setSelected(true);
                mZhanNotice.setSelected(false);
                mViewPager.setCurrentItem(TAB_SYS);
                break;
            case TAB_ZHAN:
                mGameNotice.setSelected(false);
                mSysNotice.setSelected(false);
                mZhanNotice.setSelected(true);
                mViewPager.setCurrentItem(TAB_ZHAN);
                break;
        }
    }

    @Subscribe(tags = {@Tag(PushDispatchManager.EVENT_PUSH_MSG_CENTER)})
    public void getSiteUnReadMsg(String s) {
        mPresneter.getUnReadMsgCount();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxBus.get().post(PushDispatchManager.EVENT_PUSH_MSG_CENTER, "");
    }

    @Override
    protected void onDestroy() {
        mPresneter.onDestory();
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
