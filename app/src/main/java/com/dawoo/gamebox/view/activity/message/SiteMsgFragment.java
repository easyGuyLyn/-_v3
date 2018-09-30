package com.dawoo.gamebox.view.activity.message;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.SiteMsgUnReadCount;
import com.dawoo.gamebox.mvp.presenter.MessagePresenter;
import com.dawoo.gamebox.mvp.view.ISiteMsgView;
import com.dawoo.gamebox.push.PushDispatchManager;
import com.dawoo.gamebox.util.ComFragmentManager;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.fragment.BaseFragment;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SiteMsgFragment extends BaseFragment implements ISiteMsgView {
    private static final String TYPE_FROM = "type_from";
    Unbinder unbinder;
    @BindView(R.id.btn_sys_notice)
    TextView mBtnSysNotice;
    @BindView(R.id.v_tip_sys_notice)
    TextView mVTipSysNotice;
    @BindView(R.id.btn_my_notice)
    TextView mBtnMyNotice;
    @BindView(R.id.v_tip_my_notice)
    TextView mVTipMyNotice;
    @BindView(R.id.btn_send_msg)
    Button mBtnSendMsg;
    @BindView(R.id.fragment_content)
    FrameLayout mFragmentContent;
    private int mType;

    private final int TAB_SYS_MSG = 0;
    private final int TAB_MY_MSG = 1;
    private final int TAB_SEND_MSG = 2;
    private FragmentManager mFragmentManager;
    private MessagePresenter mPresneter;

    public SiteMsgFragment() {
        // Required empty public constructor
    }

    public static SiteMsgFragment newInstance(int type) {
        SiteMsgFragment fragment = new SiteMsgFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_FROM, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(TYPE_FROM);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        ComFragmentManager.getInstance().clear();
        RxBus.get().unregister(this);
        mPresneter.onDestory();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_site_msg, container, false);
        unbinder = ButterKnife.bind(this, v);
        RxBus.get().register(this);
        initView();
        initData();
        return v;
    }

    public void initView() {
        mPresneter = new MessagePresenter(getActivity(), this);
    }

    public void getSiteUnReadMsgCount() {
        mPresneter.getUnReadMsgCount();
    }

    public void initData() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void loadData() {
        mFragmentManager = getFragmentManager();
        getSiteUnReadMsgCount();

        if (3 == mType) {
            swithTab(TAB_SEND_MSG);
        } else {
            swithTab(TAB_SYS_MSG);
        }
    }


    @OnClick({R.id.btn_sys_notice, R.id.btn_my_notice, R.id.btn_send_msg})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.btn_sys_notice:
                swithTab(TAB_SYS_MSG);
                break;
            case R.id.btn_my_notice:
                swithTab(TAB_MY_MSG);
                break;
            case R.id.btn_send_msg:
                swithTab(TAB_SEND_MSG);
                break;
        }
    }

    private void swithTab(int tab) {
        switch (tab) {
            case TAB_SYS_MSG:
                mBtnSysNotice.setSelected(true);
                mBtnMyNotice.setSelected(false);
                mBtnSendMsg.setSelected(false);
                ComFragmentManager.getInstance().switchFragment(mFragmentManager, SiteSysMsgFragment.class);
                break;
            case TAB_MY_MSG:
                mBtnSysNotice.setSelected(false);
                mBtnMyNotice.setSelected(true);
                mBtnSendMsg.setSelected(false);
                ComFragmentManager.getInstance().switchFragment(mFragmentManager, SiteMyMsgFragment.class);
                break;
            case TAB_SEND_MSG:
                mBtnSysNotice.setSelected(false);
                mBtnMyNotice.setSelected(false);
                mBtnSendMsg.setSelected(true);
                ComFragmentManager.getInstance().switchFragment(mFragmentManager, SiteUploadMsgFragment.class);
                break;
        }
    }

    @Override
    public void onGetUnReadCountResult(Object o) {
        SiteMsgUnReadCount siteMsgUnReadCount = (SiteMsgUnReadCount) o;
        if (siteMsgUnReadCount.getSysMessageUnReadCount() > 0 && mVTipSysNotice != null) {
            mVTipSysNotice.setVisibility(View.VISIBLE);
        //    mVTipSysNotice.setText(siteMsgUnReadCount.getSysMessageUnReadCount() + "");
        } else {
            mVTipSysNotice.setVisibility(View.GONE);
        }
        if (siteMsgUnReadCount.getAdvisoryUnReadCount() > 0 && mVTipMyNotice != null) {
            mVTipMyNotice.setVisibility(View.VISIBLE);
         //   mVTipMyNotice.setText(siteMsgUnReadCount.getAdvisoryUnReadCount() + "");
        } else {
            mVTipMyNotice.setVisibility(View.GONE);
        }
    }

    @Subscribe(tags = {@Tag(PushDispatchManager.EVENT_PUSH_MSG_CENTER)})
    public void pushData(String s) {
        getSiteUnReadMsgCount();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
