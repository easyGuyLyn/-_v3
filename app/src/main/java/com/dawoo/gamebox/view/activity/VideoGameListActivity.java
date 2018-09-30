package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.DensityUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.GameLink;
import com.dawoo.gamebox.bean.SiteApiRelation;
import com.dawoo.gamebox.bean.VideoGame;
import com.dawoo.gamebox.bean.VideoGameType;
import com.dawoo.gamebox.mvp.presenter.GamePresenter;
import com.dawoo.gamebox.mvp.view.ICasinoGameListView;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.AppBarStateChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 电子游戏列表
 * 带搜索
 */
public class VideoGameListActivity extends BaseActivity implements ICasinoGameListView {

    @BindView(R.id.game_name_et)
    EditText mGameNameEt;
    @BindView(R.id.search_btn)
    Button mSearchBtn;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.rl_search)
    RelativeLayout mRlSearch;
    @BindView(R.id.rv_headApiView)
    RecyclerView mRvHeadApiView;
    @BindView(R.id.appBar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.nsw)
    NestedScrollView mNsw;
    @BindView(R.id.iv_mode_2)
    ImageView ivMode2;
    private Handler mHandler = new Handler();

    public static final String API_ID = "apiId";
    public static final String API_TYPE_ID = "apiTypeId";
    public static final String GEME_NAME = "game_name";
    public static final String GEME_LIST = "game_list";


    private GamePresenter mPresenter;
    private int mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
    private int mPageSize = 35;
    private int mPageTotal;
    private VideoGameQuickAdapter mAdapter;
    private TopAdapter mTopAdapter;
    private int mApiId;
    private int mApiTypeId;
    private List<SiteApiRelation.SiteApisBean> mSiteApisBeans = new ArrayList<>();//全部游戏数据

    private List<Integer> mPageNumberTags = new ArrayList<>();//存不同tab的PageNumber
    private List<VideoGameType> mTypeList = new ArrayList<>();//全局类型
    private List<List<VideoGame.CasinoGamesBean>> mTabVideoGames = new ArrayList<>();//不同tab的数据列表
    private int mPosition;//  tabLayout position
    private String mName = "";
    private int mMode = 1;
    private boolean mHasMoreData = true;
    private boolean mIsSearch;
    private boolean mAutoClose;
    private boolean mAutoOpen;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_video_game_list);
    }

    @Override
    protected void initViews() {
        String name = getIntent().getStringExtra(GEME_NAME);
        mTitleName.setText(name);

        mAdapter = new VideoGameQuickAdapter(R.layout.recyclerview_list_item_videogame_activity_view);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view_theme, null));

        mRecyclerView.setPadding(0, DensityUtil.dp2px(this, 10), 0, 0);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mTopAdapter = new TopAdapter(R.layout.recycleview_item_gamelist_content_view_list__item);
        mRvHeadApiView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvHeadApiView.setAdapter(mTopAdapter);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.e("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    setToPosition();
                    mAutoClose = false;
                    mAutoOpen = false;
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mAutoClose = false;
                    mAutoOpen = false;
                } else if (state == State.AUTO_CLOSE) {
                    //自动回弹区域
                    mAutoClose = true;
                    mAutoOpen = false;
                } else {
                    //自动张开
                    mAutoClose = false;
                    mAutoOpen = true;
                }
            }
        });

//        mNsw.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if (mAutoClose) {
//                        mAppBarLayout.setExpanded(false);
//                    }
//                    if (mAutoOpen) {
//                        mAppBarLayout.setExpanded(true);
//                    }
//                    mAutoOpen = false;
//                    mAutoOpen = false;
//                }
//                return false;
//            }
//        });
        mNsw.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("mNsw", scrollY + "");
                if (scrollY > oldScrollY) {
                    // 向下滑动
                }

                if (scrollY < oldScrollY) {
                    // 向上滑动
                }

                if (scrollY == 0) {
                    // 顶部

                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // 底部
                    onLoadMore();
                }
            }
        });
        mGameNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mIsSearch = false;
                } else {
                    mIsSearch = true;
                }
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mApiId = intent.getIntExtra(API_ID, 0);
        mApiTypeId = intent.getIntExtra(API_TYPE_ID, 0);
        mName = mGameNameEt.getText().toString().trim();
        mPresenter = new GamePresenter(this, this);
        mPresenter.getGameTag(mApiId, mApiTypeId);
        if (intent.getParcelableArrayListExtra(GEME_LIST) != null) {
            mSiteApisBeans = (List<SiteApiRelation.SiteApisBean>) intent.getSerializableExtra(GEME_LIST);
            mTopAdapter.setNewData(mSiteApisBeans);
        }
    }

    private void setToPosition() {
        if (mSiteApisBeans == null) return;
        for (int i = 0; i < mSiteApisBeans.size(); i++) {
            if (mApiId == mSiteApisBeans.get(i).getApiId()) {
                mRvHeadApiView.smoothScrollToPosition(i);
                mTopAdapter.notifyDataSetChanged();
            }
        }
    }


    //重置数据源
    private void resetData(int apiId, int apiType, String name) {
        mApiId = apiId;
        mApiTypeId = apiType;
        mTitleName.setText(name);
        mPresenter.getGameTag(mApiId, mApiTypeId);
        mAppBarLayout.setExpanded(false);
    }

    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void onLoadResult(Object o) {
        if (o != null && o instanceof VideoGame) {
            VideoGame videoGame = (VideoGame) o;
            if (!mIsSearch) {
                mTabVideoGames.get(mPosition).clear();
                mTabVideoGames.set(mPosition, videoGame.getCasinoGames());
            }
            mPageTotal = videoGame.getPage().getPageTotal();
            Log.e("videoGamePageTotal  ", mPageTotal + "");
            mAdapter.setNewData(videoGame.getCasinoGames());
        }
    }

    @Override
    public void onLoadGameTagResult(Object o) {
        if (o != null && o instanceof List) {
            mTopAdapter.notifyDataSetChanged();
            mPageNumberTags.clear();
            mTabVideoGames.clear();
            mTypeList.clear();
            mTypeList.add(new VideoGameType("all", getString(R.string.all_games)));
            mTypeList.addAll((List<VideoGameType>) o);
            // 创建tabs数据列表
            for (int i = 0; i < mTypeList.size(); i++) {
                mTabVideoGames.add(new ArrayList<>());
                mPageNumberTags.add(i);
            }
            setGameType(mTypeList);
        }
    }

    @Override
    public void loadMoreData(Object o) {
        if (o != null && o instanceof VideoGame) {
            VideoGame videoGame = (VideoGame) o;
            List<VideoGame.CasinoGamesBean> casinoGames = videoGame.getCasinoGames();
//            Log.e("video", videoGame.getPage().getPageTotal() + "");
//            if (mAdapter != null) {
//                int size = mAdapter.getItemCount();
//                int totalPageSize = videoGame.getPage().getPageTotal();
//                if (size >= totalPageSize) {
//                    ToastUtil.showToastShort(this, getString(R.string.NO_MORE_DATA));
//                    return;
//                }
//            }
            Log.e("videoloadMoreGames.size", casinoGames.size() + "");
            if (0 != casinoGames.size()) {
                if (!mIsSearch) {
                    List<VideoGame.CasinoGamesBean> tempList = mTabVideoGames.get(mPosition);
                    tempList.addAll(videoGame.getCasinoGames());
                }
                mAdapter.notifyDataSetChanged();
            } else {
                mHasMoreData = false;
                ToastUtil.showResShort(this, R.string.NO_MORE_DATA);
            }
        }
    }


    @Override
    public void doSearch() {
        mTabVideoGames.get(mPosition).clear();
        requestData(mPosition);

    }

    @Override
    public void onLoadGameLink(Object o) {
        GameLink gameLink = (GameLink) o;
        ActivityUtil.startWebView(gameLink.getGameLink(), gameLink.getGameMsg(), ConstantValue.WEBVIEW_TYPE_GAME_FULLSCREEN_ALWAYS, 3, mApiId + "");
    }


    public void onLoadMore() {
        if (!mHasMoreData) return;
        mPageNumber = mPageNumberTags.get(mPosition);
        mPageNumber++;
        mPageNumberTags.set(mPosition, mPageNumber);
        if (mPageNumber > mPageTotal) {
            Log.e("video", "mPageNumber " + mPageNumber + "  mPageTotal " + mPageTotal);
            ToastUtil.showResShort(this, R.string.NO_MORE_DATA);
            return;
        }
        mName = mGameNameEt.getText().toString().trim();
        if (0 == mPosition) {
            mPresenter.loadMoreCasinoGameList(mApiId, mApiTypeId, mPageNumber, mPageSize, mName);
        } else {
            VideoGameType videoGameType = mTypeList.get(mPosition);
            mPresenter.loadMoreCasinoGameList(mApiId, mApiTypeId, mPageNumber, mPageSize, mName, videoGameType.getValue());
        }
    }


    @OnClick({R.id.left_btn, R.id.iv_search, R.id.iv_mode_2, R.id.search_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.iv_search:
                if (mRlSearch.getVisibility() == View.VISIBLE) {
                    mRlSearch.setVisibility(View.GONE);
                } else {
                    mRlSearch.setVisibility(View.VISIBLE);
                }
                break;
//            case R.id.iv_mode_1:
//                if (mMode == 2) return;
//                mMode = 2;
//                chaneListUIMode();
//                break;
            case R.id.iv_mode_2:
//                if (mMode == 1) return;
//                mMode = 1;
                if (mMode == 1) {
                    mMode = 2;
                    ivMode2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_column));
                } else {
                    mMode = 1;
                    ivMode2.setImageDrawable(getResources().getDrawable(R.mipmap.three_hover));
                }
                chaneListUIMode();
                break;
            case R.id.search_btn:
                doSearch();
                break;
        }
    }


    private void chaneListUIMode() {
        List<VideoGame.CasinoGamesBean> data = mAdapter.getData();
        if (mMode == 1) {
            mAdapter = new VideoGameQuickAdapter(R.layout.recyclerview_list_item_videogame_activity_view);
            mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view_theme, null));
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        } else if (mMode == 2) {
            mAdapter = new VideoGameQuickAdapter(R.layout.recyclerview_list_item_videogame_another);
            mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view_theme, null));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setNewData(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class VideoGameQuickAdapter extends BaseQuickAdapter {

        public VideoGameQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            VideoGame.CasinoGamesBean casinoGamesBean = (VideoGame.CasinoGamesBean) item;
            String name = casinoGamesBean.getName();
            TextView nameTv = helper.getView(R.id.game_name_tv);
            if (TextUtils.isEmpty(name)) {
                return;
            }

//            if (getLength(name) > 9) {
//                nameTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtil.sp2px(VideoGameListActivity.this, 12));
//            }
            nameTv.setText(name);
            String url = NetUtil.handleUrl(casinoGamesBean.getCover());
            RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher_round);
            GlideApp.with(mContext).load(url).apply(options).into((ImageView) helper.getView(R.id.game_icon_iv));
            helper.itemView.setOnClickListener(v -> {
                SoundUtil.getInstance().playVoiceOnclick();
                /* startWebView(casinoGamesBean.getGameLink(),casinoGamesBean.getGameMsg());*/
                mPresenter.getGameLink(casinoGamesBean.getApiId(), casinoGamesBean.getApiTypeId(), casinoGamesBean.getGameId(), casinoGamesBean.getCode());
            });

            if (mMode == 2) {
                helper.setText(R.id.game_api_tv, mTitleName.getText().toString());
                if (helper.getAdapterPosition() % 2 == 0) {
                    helper.itemView.setBackgroundResource(R.color.bgColor);
                } else {
                    helper.itemView.setBackgroundResource(R.color.white);
                }
                if (mTypeList.get(mPosition) == null) return;
                helper.setText(R.id.game_type_tv, mTypeList.get(mPosition).getValue());

            }

        }
    }


    private void setGameType(List<VideoGameType> list) {
        mTabLayout.removeAllTabs();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                int tabSize = mTabVideoGames.get(0).size();
                mGameNameEt.getText().clear();
                mPosition = tab.getPosition();
                requestData(mPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        VideoGameType videoGameType = null;
        for (int i = 0; i < list.size(); i++) {
            videoGameType = list.get(i);
            if (0 == i) {
                mTabLayout.addTab(mTabLayout.newTab().setText(videoGameType.getValue()), true);
            } else {
                mTabLayout.addTab(mTabLayout.newTab().setText(videoGameType.getValue()));
            }

        }
    }

    /**
     * 请求游戏列表数据
     *
     * @param position 游戏分类
     */
    void requestData(int position) {
        mHasMoreData = true;
        List list = mTabVideoGames.get(position);
        mName = mGameNameEt.getText().toString().trim();
        if (0 == list.size()) { // 请求数据
            mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
            mPageNumberTags.set(position, mPageNumber);
            if (0 == position) {
                mPresenter.getCasinoGameList(mApiId, mApiTypeId, mPageNumber, mPageSize, mName);
            } else {
                VideoGameType videoGameType = mTypeList.get(mPosition);
                Log.e("videoGameType", videoGameType.getKey());
                Log.e("videoGamePage", mPageNumber + "");
                mPresenter.getCasinoGameList(mApiId, mApiTypeId, mPageNumber, mPageSize, mName, videoGameType.getKey());
            }
        } else {
            // 填充数据
            mAdapter.setNewData(list);
        }
    }


    private class TopAdapter extends BaseQuickAdapter {

        public TopAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            SiteApiRelation.SiteApisBean siteApisBean = (SiteApiRelation.SiteApisBean) item;
            String url = NetUtil.handleUrl(siteApisBean.getCover());
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_round);
            GlideApp.with(mContext)
                    .load(url)
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (siteApisBean.getApiId() == mApiId) {
                                resource.setAlpha(255);
                            } else {
                                resource.setAlpha(150);
                            }
                            return false;
                        }
                    })
                    .into((ImageView) helper.getView(R.id.item_img_iv));
            helper.setText(R.id.item_name_tv, siteApisBean.getName());

            if (siteApisBean.getApiId() == mApiId) {
                ((TextView) helper.getView(R.id.item_name_tv)).setTextColor(getResources().getColor(R.color.white));
                helper.getView(R.id.iv_up).setVisibility(View.VISIBLE);
            } else {
                ((TextView) helper.getView(R.id.item_name_tv)).setTextColor(getResources().getColor(R.color.text_color_gray_cccccc));
                helper.getView(R.id.iv_up).setVisibility(View.INVISIBLE);
            }

            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetData(siteApisBean.getApiId(), siteApisBean.getApiTypeId(), siteApisBean.getName());
                }
            });

        }
    }


}
