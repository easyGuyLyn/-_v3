package com.dawoo.gamebox.adapter.HomeAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.adapter.BaseViewHolder;
import com.dawoo.gamebox.adapter.HomeAdapter.AdapterSet.BannerConfigInit;
import com.dawoo.gamebox.bean.Notice;
import com.dawoo.gamebox.bean.SiteApiRelation;
import com.dawoo.gamebox.bean.SiteApiRelation.SiteApisBean;
import com.dawoo.gamebox.bean.SiteTemp;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.NoticeDialog;
import com.dawoo.gamebox.view.view.marqueeText;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by benson on 17-12-24.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final int BANNER = 0; //广告轮播
    private final int NOTICE = 1; // 公告
    private final int TITLE_LIST_ONE = 2; // 标题列表1
    private final int TITLE_LIST_SUB = 3; // 标题列表2 副标题
    private final int CONTENT_ITEM_LIST = 4; // 条目列表
    private final int BOTTOM_COPRITY = 5; // 版权和填充高度
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final FragmentManager mFm;
    private final View.OnClickListener mListener;
    Handler mHandler = new Handler();
    private List<com.dawoo.gamebox.bean.Banner.BannerBean> mBanners = new ArrayList<>();
    private List<Notice.AnnouncementBean> mNotices = new ArrayList<>();
    private List<SiteApiRelation> mSiteLevelOneList = new ArrayList<>();
    // 第二级标题
    private List<SiteApisBean> mTilteSubDataList = new ArrayList<>();
    // game条目列表
    private List<SiteApisBean> mGameItemListUnLevel = new ArrayList<>();
    private List<SiteApisBean.GameListBean> mGameItemListLevel = new ArrayList<>();


    private BannerViewHolder mBannerViewHolder;
    private NoticeViewHolder mNoticeViewHolder;
    private TitleSubViewHolder mTitleSubViewHolder;

    private boolean isOneSelected = false;
    private boolean isSubSelected = false;
    private TitleOneViewHolder mTitleOneViewHolder;

    private Animation mAnimation;

    public HomeFragmentAdapter(Context context, FragmentManager fm, View.OnClickListener listener) {
        mContext = context;
        mFm = fm;
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BANNER:
                mBannerViewHolder = new BannerViewHolder(mLayoutInflater.inflate(R.layout.recycleview_item_homefragment_banner_view, parent, false));
                return mBannerViewHolder;
            case NOTICE:
                mNoticeViewHolder = new NoticeViewHolder(mLayoutInflater.inflate(R.layout.recycleview_item_homefragment_notice_view, parent, false));
                return mNoticeViewHolder;
            case TITLE_LIST_ONE:

                mTitleOneViewHolder = new TitleOneViewHolder(mLayoutInflater.inflate(R.layout.recycleview_item_homefragment__title_list_one_view, parent, false));
                return mTitleOneViewHolder;
            case TITLE_LIST_SUB:
                mTitleSubViewHolder = new TitleSubViewHolder(mLayoutInflater.inflate(R.layout.recycleview_item_homefragment__title_list_sub_view, parent, false));
                return mTitleSubViewHolder;
            case CONTENT_ITEM_LIST:
                return new GameItemViewHolder(mLayoutInflater.inflate(R.layout.recycleview_item_homefragment_game_view_list_item, parent, false));
            case BOTTOM_COPRITY:
                return new BottomCopyRightViewHolder(mLayoutInflater.inflate(R.layout.recycleview_item_homefragment_bottom_copy_right_item, parent, false));
            default:
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView();
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return BANNER;
            case 1:
                return NOTICE;
            case 2:
                return TITLE_LIST_ONE;
            case 3:
                return TITLE_LIST_SUB;
            case 4:
                return CONTENT_ITEM_LIST;
            case 5:
                return BOTTOM_COPRITY;
        }

        return CONTENT_ITEM_LIST;
    }


    public void setBanners(List list) {
        mBanners.clear();
        mBanners.addAll(list);
        notifyItemChanged(BANNER);
    }

    public void setNotice(List list) {
        mNotices.clear();
        mNotices.addAll(list);
        notifyItemChanged(NOTICE);
    }

    public void setContent(List list) {
        mSiteLevelOneList.clear();
        mSiteLevelOneList.addAll(list);
        notifyItemChanged(TITLE_LIST_ONE);
    }


    public void setBannerStartAutoPlay() {
        if (mBannerViewHolder != null) {
            mBannerViewHolder.mBanner.startAutoPlay();
        }
    }

    public void setBannerStopAutoPlay() {
        if (mBannerViewHolder != null) {
            mBannerViewHolder.mBanner.stopAutoPlay();
        }
    }

//    public void setNoticeStartAutoScroll() {
//        if (mNoticeViewHolder != null) {
//            mNoticeViewHolder.mNoticeTv.startAutoScroll();
//        }
//    }

//    public void noticeReleaseResources() {
//        if (mNoticeViewHolder != null) {
//            mNoticeViewHolder.mNoticeTv.releaseResources();
//        }
//    }

    /**
     * 图片轮播
     */
    class BannerViewHolder extends BaseViewHolder {
        @BindView(R.id.banner)
        Banner mBanner;
        @BindView(R.id.banner_close_iv)
        ImageView mBannerCloseIv;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindView() {
            super.onBindView();
            List images = new ArrayList();
            List titles = new ArrayList();
            List<String> Links = new ArrayList();
       //     mHomeBannerAdapter = new HomeBannerAdapter(R.layout.item_home_banner);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            rcRecyclerView.setLayoutManager(layoutManager);
//            rcRecyclerView.setAdapter(mHomeBannerAdapter);
//            if (indicatorList != null) {
//                indicatorList.clear();
//            }
            for (int i = 0; i < mBanners.size(); i++) {
                images.add(NetUtil.handleUrl(mBanners.get(i).getCover()));
                titles.add(mBanners.get(i).getName());
                Links.add(mBanners.get(i).getLink());
            }

            BannerConfigInit init = new BannerConfigInit(mBanner, images, titles, new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    String url = Links.get(position);
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    ActivityUtil.startWebView(NetUtil.handleUrl(url), "", ConstantValue.WEBVIEW_TYPE_THIRD_ORDINARY, 1, "");
                }
            });

            init.start();

            mBannerCloseIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                    param.height = 1;
                    param.width = 1;
                    itemView.setLayoutParams(param);
                    itemView.setVisibility(View.GONE);
                }
            });
        }
    }


    /**
     * 公告栏
     */
    class NoticeViewHolder extends BaseViewHolder {
        @BindView(R.id.notice_tv)
        marqueeText mNoticeTv;
        @BindView(R.id.btn_notice)
        Button mBtnNotice;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindView() {
            super.onBindView();
            //mNoticeTv.setText("公告，来自adapter");
            ArrayList<String> titleList = new ArrayList<>();
            String noticeTv = "";
            for (Notice.AnnouncementBean bean : mNotices) {
                titleList.add(bean.getContent());
                noticeTv = noticeTv + bean.getContent();
            }
            Log.e("noticeTv", noticeTv);
            mNoticeTv.setText(noticeTv);
            mNoticeTv.setOnClickListener(v -> {
                SoundUtil.getInstance().playVoiceOnclick();
                NoticeDialog dialog = new NoticeDialog(mContext, titleList);
            });

        }
    }


    private List<ImageView> mImageViews = new ArrayList<>();

    /**
     * 第一级标题
     */
    class TitleOneViewHolder extends BaseViewHolder {
        @BindView(R.id.tab_layout)
        TabLayout mTabLayout;

        public TitleOneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindView() {
            super.onBindView();
            mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.home_game_title);
            mAnimation.setFillAfter(true);
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    isOneSelected = true;
                    isSubSelected = false;
                    setSubTitleData(tab.getPosition());

                    for (int i = 0; i < mImageViews.size(); i++) {
                        if (i == tab.getPosition()) {
                            mImageViews.get(i).startAnimation(mAnimation);
                        } else {
                            mImageViews.get(i).clearAnimation();
                        }
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            List<String> titles = new ArrayList<>();
            List<String> tabImgs = new ArrayList<>();

            mTabLayout.removeAllTabs();
            // 获取标题名和标题icon
            for (int i = 0; i < mSiteLevelOneList.size(); i++) {
                SiteApiRelation siteApiRelation = mSiteLevelOneList.get(i);
                titles.add(siteApiRelation.getApiTypeName());
                tabImgs.add(NetUtil.handleUrl(siteApiRelation.getCover()));

                if (0 == i) {
                    mTabLayout.addTab(mTabLayout.newTab(), true);
                    setSubTitleData(0);
                } else {
                    mTabLayout.addTab(mTabLayout.newTab(), false);
                }
            }
            mImageViews.clear();
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabView(titles, tabImgs, i, mTabLayout));
            }

            if (mTabLayout.getTabCount() > 0) {
                mImageViews.get(0).startAnimation(mAnimation);
            }
        }
    }


    /**
     * 自定义设置第一行标题
     *
     * @param titles
     * @param tabImgs
     * @param position
     * @return
     */
    public View getTabView(List<String> titles, List<String> tabImgs, int position, TabLayout mTabLayout) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_home_content_one_tab_layot_item_custom_view, null);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_tab);
        tvTitle.setText(titles.get(position));
        ImageView imgTab = (ImageView) v.findViewById(R.id.img_tab);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_round);
        GlideApp.with(mContext).load(tabImgs.get(position)).apply(options).into(imgTab);
        mImageViews.add(imgTab);
        return v;
    }


    void setSubTitleData(int position) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 获取标题名和标题icon
                SiteApiRelation siteApiRelationBean = mSiteLevelOneList.get(position);
                LogUtils.i("选中:" + position);
                if (siteApiRelationBean.isLevel()) {
                    // 有二级标题
                    isOneSelected = true;
                    isSubSelected = true;
                    List<SiteApisBean> siteApisBeans = siteApiRelationBean.getSiteApis();
                    mTilteSubDataList.clear();
                    mTilteSubDataList.addAll(siteApisBeans);

                    notifyItemChanged(TITLE_LIST_SUB);

                } else {
                    //  没有二级标题
                    isOneSelected = true;
                    isSubSelected = false;
                    notifyItemChanged(TITLE_LIST_SUB);
                    mGameItemListUnLevel.clear();
                    mGameItemListUnLevel.addAll(siteApiRelationBean.getSiteApis());
                    notifyItemChanged(CONTENT_ITEM_LIST);
                }
            }
        });
    }


    /**
     * 第二级标题
     */
    class TitleSubViewHolder extends BaseViewHolder {
        @BindView(R.id.sub_tab_layout)
        TabLayout mSubTabLayout;

        public TitleSubViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindView() {
            super.onBindView();


            mSubTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    setLeveLGameListDataChange(tab.getPosition());
                    LogUtils.i("选中第二级标题的第:" + tab.getPosition() + "项");
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            int position = getAdapterPosition();
            LogUtils.e(position);
            List<String> titles = new ArrayList<>();
            List<String> tabImgs = new ArrayList<>();
            mSubTabLayout.removeAllTabs();


            // 获取标题名和标题icon
            for (int i = 0; i < mTilteSubDataList.size(); i++) {
                SiteApisBean siteApisBean = mTilteSubDataList.get(i);
                titles.add(siteApisBean.getName());
                tabImgs.add(NetUtil.handleUrl(siteApisBean.getCover()));
                if (0 == i) {
                    mSubTabLayout.addTab(mSubTabLayout.newTab().setText("0"), true);
                } else {
                    mSubTabLayout.addTab(mSubTabLayout.newTab().setText("" + i), false);
                }
            }

            for (int i = 0; i < mSubTabLayout.getTabCount(); i++) {
                mSubTabLayout.getTabAt(i).setCustomView(getSubTabView(titles, tabImgs, i));
            }

            if (isSubSelected && 1 == mTilteSubDataList.size()) {
                setVisibility(false);
                return;
            }

            if (isSubSelected) {
                setVisibility(true);
            } else if (!isSubSelected) {
                setVisibility(false);
            }
        }

        public boolean isVisibility() {
            if (View.VISIBLE == itemView.getVisibility()) {
                return true;
            } else if (View.GONE == itemView.getVisibility()) {
                return false;
            }
            return false;
        }


        public void setVisibility(boolean isVisible) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                    if (isVisible) {
                        param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                        param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        itemView.setVisibility(View.VISIBLE);
                    } else {
                        itemView.setVisibility(View.GONE);
                        param.height = 0;
                        param.width = 0;
                    }

                    itemView.setLayoutParams(param);
                }
            });
        }
    }


    void setLeveLGameListDataChange(int position) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                SoundUtil.getInstance().playVoiceOnclick();
                if (isSubSelected) {
                    SiteApisBean siteApisBean = mTilteSubDataList.get(position);

                    mGameItemListLevel.clear();
                    if (siteApisBean.getGameList() != null) {
                        mGameItemListLevel.addAll(siteApisBean.getGameList());
                    }
                    notifyItemChanged(CONTENT_ITEM_LIST);
                }
            }
        });

    }


    /**
     * 自定义设置第二行标题
     *
     * @param titles
     * @param tabImgs
     * @param position
     * @return
     */
    public View getSubTabView(List<String> titles, List<String> tabImgs, int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_home_content_two_tab_layot_item_custom_view, null);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_tab);
        tvTitle.setText(titles.get(position));
        ImageView imgTab = (ImageView) v.findViewById(R.id.img_tab);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_round);
        GlideApp.with(mContext).load(tabImgs.get(position)).apply(options).into(imgTab);
        return v;
    }


    /**
     * 游戏列表
     */
    class GameItemViewHolder extends BaseViewHolder {
        @BindView(R.id.game_item_list)
        RecyclerView mGameItemList;


        public GameItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindView() {
            super.onBindView();
            int position = getAdapterPosition();
            LogUtils.e(position);
            GameItemQuickAdapter adapter = new GameItemQuickAdapter(R.layout.recycleview_item_homefragment_game_view_list_one_item);
            mGameItemList.setFocusableInTouchMode(false);
            mGameItemList.requestFocus();
            mGameItemList.setItemAnimator(new DefaultItemAnimator());
            mGameItemList.setLayoutManager(new GridLayoutManager(mContext, 3));
            mGameItemList.setAdapter(adapter);


            if (0 != mGameItemListLevel.size() && isOneSelected && isSubSelected) {
                adapter.setNewData(mGameItemListLevel);
            } else if (0 != mGameItemListUnLevel.size() && isOneSelected && !isSubSelected) {
                adapter.setNewData(mGameItemListUnLevel);
            }
        }
    }


    class GameItemQuickAdapter extends BaseQuickAdapter {


        public GameItemQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, Object item) {
            String name = "", cover = "";
            boolean level = false;
            SiteApisBean.GameListBean gameListBean = null;
            SiteApisBean siteApisBean = null;
            if (0 != mGameItemListLevel.size() && isOneSelected && isSubSelected) {
                gameListBean = (SiteApisBean.GameListBean) item;
                name = gameListBean.getName();
                cover = gameListBean.getCover();
                level = true;
            } else if (0 != mGameItemListUnLevel.size() && isOneSelected && !isSubSelected) {
                siteApisBean = (SiteApisBean) item;
                name = siteApisBean.getName();
                cover = siteApisBean.getCover();
                level = false;
            }

            String url = NetUtil.handleUrl(cover);
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_round);
            GlideApp.with(mContext).load(url).apply(options).into((ImageView) helper.getView(R.id.item_img_iv));
            helper.setText(R.id.item_name_tv, name);
            boolean finalLevel = level;
            SiteApisBean.GameListBean finalGameListBean = gameListBean;
            SiteApisBean finalSiteApisBean = siteApisBean;

            SiteTemp siteTemp = new SiteTemp(finalLevel, finalGameListBean, finalSiteApisBean, helper.getAdapterPosition(), mGameItemListUnLevel);
            helper.itemView.setTag(R.id.list_item_data_id, siteTemp);
            helper.itemView.setOnClickListener(mListener);
        }
    }


    class BottomCopyRightViewHolder extends BaseViewHolder {
        @BindView(R.id.copy_right_tv)
        TextView copyRightTv;


        public BottomCopyRightViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindView() {
            super.onBindView();
            copyRightTv.setText(ConstantValue.COPY_RIGHT);
        }
    }
}
