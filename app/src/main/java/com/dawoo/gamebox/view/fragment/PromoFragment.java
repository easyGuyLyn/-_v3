package com.dawoo.gamebox.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.adapter.promoAapter.PromoFragmentGrideAdapter;
import com.dawoo.gamebox.bean.ActivityType;
import com.dawoo.gamebox.bean.ActivityTypeList;
import com.dawoo.gamebox.mvp.presenter.PromoFramentPresenter;
import com.dawoo.gamebox.mvp.view.IPromoFragmentView;
import com.dawoo.gamebox.mvp.view.SwiperRefreshLayout;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.MSPropties;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.swipetoloadlayout.RefreshHeaderView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PromoFragment extends BaseFragment implements IPromoFragmentView, OnRefreshListener, OnLoadMoreListener, SwiperRefreshLayout {

    @BindView(R.id.swipe_target)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.promo_gride)
    GridView mpromogride;
    Unbinder unbinder;
    @BindView(R.id.gSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private PromoFramentPresenter mPresenter;
    private PromoQuickAdapter mQuickAdapter;
    public PromoFragmentGrideAdapter loadGrideDateAdapter;
    private int pageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
    private int pageSize = ConstantValue.RECORD_LIST_PAGE_SIZE;
    private String activityKeyStr;
    private List<List<ActivityTypeList.ListBean>> activityTypeLists = new ArrayList<>();
    private List<ActivityType> activityTypes = new ArrayList<>();
    private int mPosition;

    private List<ActivityType> allActivityTypes = new ArrayList<>();


    public PromoFragment() {
    }

    public static PromoFragment newInstance() {
        PromoFragment fragment = new PromoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.get().unregister(this);
        mPresenter.onDestory();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_promo, container, false);
        unbinder = ButterKnife.bind(this, v);

        initViews(v);
        initData();
        return v;
    }

    private void initViews(View v) {
        mTitleName.setText(getString(R.string.promo_activity));
        mSwipeToLoadLayout.setBackgroundColor(getResources().getColor(R.color.promo_bg));
        mRecycleView.setBackgroundColor(getResources().getColor(R.color.promo_bg));
        mQuickAdapter = new PromoQuickAdapter(R.layout.recycleview_item_promofragment_activity_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setPadding(10, 10, 9, 0);
        mQuickAdapter.setEmptyView(LayoutInflater.from(mContext).inflate(R.layout.empty_view, null));
        mRecycleView.setAdapter(mQuickAdapter);


        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mSwipeToLoadLayout.setRefreshEnabled(true);
        mSwipeToLoadLayout.setLoadMoreEnabled(false);
        mSwipeRefreshLayout.setOnRefreshListener(this::Refresh);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent));
    }

    void initData() {
        RxBus.get().register(this);
        mPresenter = new PromoFramentPresenter(mContext, this);
    }

    @Override
    protected void loadData() {
        mPresenter.getActivityType();

    }

    @Override
    public void onPromoResult(Object o) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (o != null && o instanceof List) {
            activityTypes = (List<ActivityType>) o;
            allActivityTypes = MSPropties.getALLTypeDate(activityTypes, getActivity());  //加载带有全部的数据
            Log.e("TAG", allActivityTypes.size() + "");
            activityKeyStr = allActivityTypes.get(0).getActivityKey();
            mPresenter.getPromoListResult(pageNumber, pageSize, activityKeyStr);
            mPosition = 0;

            MSPropties.activityTypeLists(allActivityTypes, activityTypeLists); //创建空的集合对象
            MSPropties.height(allActivityTypes, mSwipeRefreshLayout, getContext());  //根据长度设置高度
            loadGrideDateAdapter = new PromoFragmentGrideAdapter(mContext, allActivityTypes);
            mpromogride.setAdapter(loadGrideDateAdapter);
            mpromogride.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SoundUtil.getInstance().playVoiceOnclick();
                    mPosition = position;
                    if (o != null && o instanceof List) {
                        List list = activityTypeLists.get(position);
                        if (list.size() == 0) {
                            resetDate();
                            loadGrideDateAdapter.setSeclection(position);
                            loadGrideDateAdapter.notifyDataSetChanged();
                            if (mPosition == 0) {
                                activityKeyStr = allActivityTypes.get(0).getActivityKey();
                            } else {
                                activityKeyStr = allActivityTypes.get(position).getActivityKey();
                            }
                            activityKeyStr = allActivityTypes.get(position).getActivityKey();
                            mPresenter.getPromoListResult(pageNumber, pageSize, activityKeyStr);

                        } else {
                            loadGrideDateAdapter.setSeclection(position);
                            loadGrideDateAdapter.notifyDataSetChanged();
                            mQuickAdapter.setNewData(list);
                        }
                    }

                }
            });
        }
    }


    /**
     * 重置数据
     */
    private void resetDate() {
        pageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
        mQuickAdapter.notifyDataSetChanged();
        mSwipeToLoadLayout.setRefreshEnabled(true);
        mSwipeToLoadLayout.setLoadMoreEnabled(false);
    }

    /**
     * 加载下面列表的[数据
     *
     * @param o
     */
    @Override
    public void onPromoListResult(Object o) {

        ActivityTypeList activityTypeList = (ActivityTypeList) o;
        if (mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }

        if (activityTypeList.getList() != null) {
            mQuickAdapter.setNewData(activityTypeList.getList());
            activityTypeLists.get(mPosition).clear();
            activityTypeLists.set(mPosition, activityTypeList.getList());
        }

        if (mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }
    }


    /**
     * 加载更多列表数据
     *
     * @param o
     */
    @Override
    public void loadMoreListDate(Object o) {
        ActivityTypeList activityTypeList = (ActivityTypeList) o;

        if (mSwipeToLoadLayout.isLoadingMore()) {
            mSwipeToLoadLayout.setLoadingMore(false);
        }


        if (mQuickAdapter != null) {
            int size = mQuickAdapter.getItemCount();
            if (size >= activityTypeList.getTotal()) {
                ToastUtil.showToastShort(getActivity(), getString(R.string.NO_MORE_DATA));
                return;
            }
        }

        if (activityTypeList.getList() != null) {
            mQuickAdapter.addData(activityTypeList.getList());
        } else {
            ToastUtil.showToastShort(getActivity(), getString(R.string.NO_MORE_DATA));
        }

    }

    @Override
    public void onLoadMore() {
        pageNumber++;
        mPresenter.getLoadMoreListDate(pageNumber, pageSize, activityKeyStr);

    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        if (allActivityTypes.size() <= 0) {
            mSwipeToLoadLayout.setRefreshing(false);
            return;
        }
        mPresenter.getPromoListResult(pageNumber, pageSize, allActivityTypes.get(mPosition).getActivityKey());
        List list = activityTypeLists.get(mPosition);
        mQuickAdapter.setNewData(list);
    }

    @Override
    public void Refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getActivityType();
        onRefresh();
    }


    class PromoQuickAdapter extends BaseQuickAdapter {
        public PromoQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            ActivityTypeList.ListBean bean = (ActivityTypeList.ListBean) item;
            String url = NetUtil.handleUrl(bean.getPhoto());
            Glide.with(mContext).load(url).into((ImageView) helper.getView(R.id.promo_iv));

            helper.setText(R.id.tv_loaddate, bean.getName());
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundUtil.getInstance().playVoiceOnclick();
                    if (!TextUtils.isEmpty(bean.getUrl())) {
//                        String url =  bean.getUrl();
//                        ActivityUtil.startWebView(url, "", ConstantValue.WEBVIEW_TYPE_ORDINARY, 1, "");
                        ActivityUtil.startLtDomainWebview(bean.getUrl(), getActivity(),ConstantValue.WEBVIEW_TYPE_ORDINARY,"");

                    } else {
                        ToastUtil.showToastShort(getActivity(), getString(R.string.url_null));
                    }
                }
            });
        }
    }

    /**
     * 登录成功后，回调加载账户
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGINED)})
    public void loginedReload(String s) {
        allActivityTypes.clear();
        activityTypeLists.clear();
        mPresenter.getActivityType();

    }


    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_NETWORK_EXCEPTION)})
    public void shrinkRefreshView(String s) {
        LogUtils.d(s);
        //  收起刷新
        if (mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}

