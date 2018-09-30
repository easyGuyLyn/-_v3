package com.dawoo.gamebox.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.MyPromo;
import com.dawoo.gamebox.mvp.presenter.RecordPresenter;
import com.dawoo.gamebox.mvp.view.IMypromoListView;
import com.dawoo.gamebox.view.view.HeaderView;
import com.dawoo.gamebox.view.view.swipetoloadlayout.RefreshHeaderView;

import java.util.Date;

import butterknife.BindView;

/**
 * 优惠记录
 */
public class PromoRecordActivity extends BaseActivity implements IMypromoListView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.swipe_target)
    RecyclerView mPremoRecordRecycleview;

    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;

    private PRQucikAdapter mAdapter;
    private int mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
    private int mPageSize = ConstantValue.RECORD_LIST_PAGE_SIZE;
    private RecordPresenter mPresenter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_promo_record);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }


    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.promo_record_acitivity), true);
        mAdapter = new PRQucikAdapter(R.layout.recycleview_item_promo_record_activity_view);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null));
        mPremoRecordRecycleview.setLayoutManager(new LinearLayoutManager(this));
        mPremoRecordRecycleview.setAdapter(mAdapter);

        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mSwipeToLoadLayout.setRefreshEnabled(true);
        mSwipeToLoadLayout.setLoadMoreEnabled(true);
    }

    @Override
    protected void initData() {
        mPresenter = new RecordPresenter(this, this);
        mPresenter.getMypromoList(mPageNumber, mPageSize);
    }


    class PRQucikAdapter extends BaseQuickAdapter {
        public PRQucikAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            MyPromo.ListBean mypromobean = (MyPromo.ListBean) item;
            helper.setText(R.id.title_name_tv, mypromobean.getActivityName());
            /**
             * 如果数据不同则加载此时的数据
             */
            String padStr = String.valueOf(mypromobean.getPreferentialAudit());
            Log.e("TAG", "截取过后字符串的长度" + padStr.substring(0, padStr.indexOf(".")));

            if (padStr.substring(0, padStr.indexOf(".")).equals("0"))
                helper.setText(R.id.tip_tv, mypromobean.getPreferentialAuditName());
            else
                helper.setText(R.id.tip_tv, padStr + "倍" + "稽核");

            helper.setText(R.id.time_tv, DateTool.convert2String(new Date(mypromobean.getApplyTime()), DateTool.FMT_DATE_TIME)); //转换成年月日
            helper.setText(R.id.money_tv, "¥ " + String.valueOf(mypromobean.getPreferentialValue())); //剩余金额
            helper.setText(R.id.status_tv, mypromobean.getCheckStateName()); //状态信息
            if (mypromobean.getCheckState().equals("success") || mypromobean.getCheckStateName().equals("已发放")) {//已发送
                helper.setBackgroundRes(R.id.image_background, R.mipmap.green);
            } else if (mypromobean.getCheckState().equals("0") || mypromobean.getCheckStateName().equals("进行中") || mypromobean.getCheckStateName().equals("待审核")) {//未通过
                helper.setBackgroundRes(R.id.image_background, R.mipmap.orange);
            } else {//待审核
                helper.setBackgroundRes(R.id.image_background, R.mipmap.gray);
            }
        }
    }

    @Override
    public void onLoadResult(Object o) {
        MyPromo myPromo = (MyPromo) o;
        setData(myPromo);
        if (mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }
    }

    private void setData(MyPromo myPromo) {
        if (myPromo.getList() != null) {
            mAdapter.setNewData(myPromo.getList());
        }

        if (mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }
    }


    @Override
    public void loadMoreData(Object o) {
        MyPromo myPromo = (MyPromo) o;
        if (mSwipeToLoadLayout.isLoadingMore()) {
            mSwipeToLoadLayout.setLoadingMore(false);
        }


        if (mAdapter != null) {
            int size = mAdapter.getItemCount();
            if (size >= myPromo.getTotalCount()) {
                ToastUtil.showToastShort(this, getString(R.string.NO_MORE_DATA));
                return;
            }
        }

        if (myPromo.getList() != null) {
            mAdapter.addData(myPromo.getList());
        } else {
            ToastUtil.showToastShort(this, getString(R.string.NO_MORE_DATA));
        }
    }

    @Override
    public void onRefresh() {
        mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
        mPresenter.getMypromoList(mPageNumber, mPageSize);
    }

    @Override
    public void onLoadMore() {
        mPageNumber++;
        mPresenter.getMorePromomoreList(mPageNumber, mPageSize);
    }
}
