package com.dawoo.gamebox.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.UserPlayerRecommend;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.view.IShareRuleRecordView;
import com.dawoo.gamebox.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jack on 18-2-13.
 */

public class ShareRuleRecodingFragmeng extends BaseFragment implements IShareRuleRecordView {


    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.log_recyclerview)
    RecyclerView logRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.share_friend)
    RelativeLayout shareFriend;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.type_tv)
    TextView typeTv;

    private SharedAdapter sharedAdapter;

    private Context context;
    private CommonPresenter mCommonPresenter;

    @Override
    protected void loadData() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_share_rule, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity();
        initView();
        initDate();
        return v;
    }

    private void initDate() {
        mCommonPresenter.getShareUserPlayerRecommend();
    }

    @Override
    public void onDestroy() {
        mCommonPresenter.onDestory();
        super.onDestroy();
    }

    private void initView() {
        mCommonPresenter = new CommonPresenter(context, this);
        logRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        sharedAdapter = new SharedAdapter(R.layout.item_sharerule);
        logRecyclerview.setAdapter(sharedAdapter);
    }

    @Override
    public void onResult(Object o) {
        UserPlayerRecommend userPlayerRecommend = (UserPlayerRecommend) o;
        moneyTv.setText(getResources().getString(R.string.share_rew_text_text) + userPlayerRecommend.getWitchWithdraw() + "元");
        if (userPlayerRecommend.getReward().equals("1")) {
            typeTv.setText(getResources().getString(R.string.share_reward_money_one) + userPlayerRecommend.getMoney() + "元");
        } else if (userPlayerRecommend.getReward().equals("2")) {
            typeTv.setText(getResources().getString(R.string.share_reward_money_two) + userPlayerRecommend.getMoney() + "元");
        } else {
            typeTv.setText(getResources().getString(R.string.share_reward_money_other) + userPlayerRecommend.getMoney() + "元");
        }

        if (userPlayerRecommend.isIsBonus()) {
            shareFriend.setVisibility(View.VISIBLE);
        } else {
            shareFriend.setVisibility(View.GONE);
        }

        if (userPlayerRecommend != null || userPlayerRecommend.getGradientTempArrayList().size() != 0) {
            sharedAdapter.setNewData(userPlayerRecommend.getGradientTempArrayList());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class SharedAdapter extends BaseQuickAdapter {

        public SharedAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            UserPlayerRecommend.GradientTempArrayListBean gradientTempArrayListBean = (UserPlayerRecommend.GradientTempArrayListBean) item;
            helper.setText(R.id.playerNum, gradientTempArrayListBean.getPlayerNum() + "以上");
            helper.setText(R.id.proportion, gradientTempArrayListBean.getProportion() + "%");
        }

    }
}
