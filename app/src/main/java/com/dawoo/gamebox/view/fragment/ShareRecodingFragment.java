package com.dawoo.gamebox.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.ActivityTypeList;
import com.dawoo.gamebox.bean.GradientTemp;
import com.dawoo.gamebox.bean.UserPlayerRecommend;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.view.IShareRuleView;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.util.StringTool;
import com.dawoo.gamebox.view.view.swipetoloadlayout.LoadMoreFooterView;
import com.dawoo.gamebox.view.view.swipetoloadlayout.RefreshHeaderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.http.PUT;

/**
 * @author jack
 * @date 18-2-13
 */

public class ShareRecodingFragment extends BaseFragment implements IShareRuleView, OnRefreshListener, OnLoadMoreListener {
    public static String DATE_MIN_TIME = " 00:00:01";
    public static String DATE_MAX_TIME = " 23:59:59";
    Unbinder unbinder;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.fv)
    TextView fv;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeToLoadLayout;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.search_btn)
    Button searchBtn;
    private ShareAdapter shareAdapter;
    private String start_time;
    private String end_time;
    private CommonPresenter mCommonPresenter;
    private Integer page = ConstantValue.share_RECORD_LIST_PAGE_SIZE;
    private Integer pageNumber = 1;

    public ShareRecodingFragment() {
    }

    public static ShareRecodingFragment newInstance() {
        ShareRecodingFragment fragment = new ShareRecodingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_share_recoding, container, false);
        unbinder = ButterKnife.bind(this, v);
        initView();
        initDate();
        return v;
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 初始化組件
     */
    private void initView() {
        String currentDate = DateTool.convert2String(new Date(System.currentTimeMillis()), DateTool.FMT_DATE);
        start_time = currentDate;
        end_time = currentDate;
        Log.e("TAG", "start_time" + start_time.toString());
        tvStartTime.setText(start_time);
        tvEndTime.setText(end_time);
        shareAdapter = new ShareAdapter(R.layout.item_share_rule);
        swipeTarget.setLayoutManager(new LinearLayoutManager(getActivity()));
        shareAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, null));
        swipeTarget.setAdapter(shareAdapter);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);
        searchBtn.setOnClickListener(view -> mCommonPresenter.gradientTempArrayList(start_time, end_time, pageNumber, page));
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        mCommonPresenter = new CommonPresenter(getContext(), this);
        mCommonPresenter.gradientTempArrayList(start_time, end_time, pageNumber, page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.setLoadingMore(true);
        pageNumber++;
        Log.e("TAG", "pageNumber" + pageNumber);
        mCommonPresenter.gradientTempLoadMore(start_time, end_time, pageNumber, page);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.setRefreshing(true);
        pageNumber = 1;
        mCommonPresenter.gradientTempArrayList(start_time, end_time, pageNumber, page);
    }

    @OnClick({R.id.search_btn, R.id.tv_start_time, R.id.fv, R.id.tv_end_time, R.id.swipe_refresh_header, R.id.swipe_target, R.id.swipe_load_more_footer, R.id.swipeToLoadLayout})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.tv_end_time:
                mCommonPresenter.selectTime(0);
                break;
            case R.id.tv_start_time:
                mCommonPresenter.selectTime(1);
                break;


        }
    }

    @Override
    public void onResult(Object o) {

        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);

        }

        if (o != null) {
            GradientTemp commandBean = (GradientTemp) o;
            shareAdapter.setNewData(commandBean.getCommand());
        }
    }

    @Override
    public void chooseStartTime(String time) {
        start_time = time;
        if (StringTool.replaceStr(start_time) > StringTool.replaceStr(end_time)) {
            tvStartTime.setText(end_time.substring(0, 10));
        } else {
            tvStartTime.setText(time.substring(0, 10));
        }
    }

    @Override
    public void chooseEndTime(String time) {
        end_time = time;
        tvEndTime.setText(time);
    }

    @Override
    public void LoadMore(Object o) {
        GradientTemp gradientTemp = (GradientTemp) o;
        int count = 0;
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (o != null) {
            if (gradientTemp.getCommand().size() == 0 || gradientTemp.getCommand() == null) {
                ToastUtil.showToastShort(getActivity(), getString(R.string.NO_MORE_DATA));
                return;
            }
            count = gradientTemp.getTotal();
            shareAdapter.addData(gradientTemp.getCommand());
            shareAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showToastShort(getContext(), getResources().getString(R.string.no_data));
        }

        if (shareAdapter != null) {
            int size = shareAdapter.getItemCount();
            if (size >= count) {
                ToastUtil.showToastShort(getActivity(), getString(R.string.NO_MORE_DATA));
                return;
            }
        }
    }

    class ShareAdapter extends BaseQuickAdapter {
        public ShareAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            GradientTemp.CommandBean command = (GradientTemp.CommandBean) item;

            helper.setText(R.id.recommendUserName, command.getRecommendUserName());
            helper.setText(R.id.createTime, DateTool.convert2String(new Date(command.getCreateTime()), DateTool.FMT_DATE) + "");
            helper.setText(R.id.status, command.getStatus());

            if (command.getRewardAmount() == null) {
                helper.setText(R.id.rewardAmount, "0");
            } else {
                helper.setText(R.id.rewardAmount, command.getRewardAmount());
            }

            int postion = helper.getLayoutPosition();
            if (postion % 2 == 0) {
                helper.setBackgroundColor(R.id.ll, getResources().getColor(R.color.white));
                helper.setBackgroundColor(R.id.recommendUserName, getResources().getColor(R.color.white));
                helper.setBackgroundColor(R.id.createTime, getResources().getColor(R.color.white));
                helper.setBackgroundColor(R.id.status, getResources().getColor(R.color.white));
                helper.setBackgroundColor(R.id.rewardAmount, getResources().getColor(R.color.white));
            } else {
                helper.setBackgroundColor(R.id.ll, getResources().getColor(R.color.white));
                helper.setBackgroundColor(R.id.recommendUserName, getResources().getColor(R.color.text_color_gray_999999));
                helper.setBackgroundColor(R.id.createTime, getResources().getColor(R.color.text_color_gray_999999));
                helper.setBackgroundColor(R.id.status, getResources().getColor(R.color.text_color_gray_999999));
                helper.setBackgroundColor(R.id.rewardAmount, getResources().getColor(R.color.text_color_gray_999999));
            }
        }
    }
}
