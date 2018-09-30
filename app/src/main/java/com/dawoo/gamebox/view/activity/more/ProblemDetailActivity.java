package com.dawoo.gamebox.view.activity.more;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.HelpDetailsBean;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.view.IMineHelpDerailsView;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 问题详情
 * Created by jack on 18-3-25.
 */

public class ProblemDetailActivity extends BaseActivity implements IMineHelpDerailsView {
    public static final String SEARCH_ID = "search_id";
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.mrecyclerView)
    RecyclerView mRecyclerView;
    private CommonPresenter mPresenter;
    private HelpDetailsAdapter helpDetailsAdapter;
    private Handler mHandler;
    private List<HelpDetailsBean.ListBean> mLists;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_help_details);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader("问题详情", true);
        helpDetailsAdapter = new HelpDetailsAdapter(R.layout.item_help_detils);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.setAdapter(helpDetailsAdapter);
        helpDetailsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                View viewPrent = mRecyclerView.getLayoutManager().findViewByPosition(position);
                if (viewPrent == null)
                    return;
                View view1 = viewPrent.findViewById(R.id.child_name);
                if (view1 != null) {
                    if (view1.getVisibility() == View.GONE) {
                        mLists.get(position).setChildOpen(true);
                    } else {
                        mLists.get(position).setChildOpen(false);
                    }
                    helpDetailsAdapter.notifyItemChanged(position);
                }

            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter(this, this);
        mPresenter.helpDetail(getIntent().getStringExtra(SEARCH_ID));
        mHandler = new Handler();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void onResult(Object o) {
        if (o != null && o instanceof HelpDetailsBean) {
            HelpDetailsBean helpDetailsBean = (HelpDetailsBean) o;
            mLists = helpDetailsBean.getList();
            helpDetailsAdapter.setNewData(mLists);
        }
    }


    class HelpDetailsAdapter extends BaseQuickAdapter {

        public HelpDetailsAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            HelpDetailsBean.ListBean listBean = (HelpDetailsBean.ListBean) item;
            helper.setText(R.id.parent_name, Html.fromHtml(listBean.getHelpTitle()));
            WebView childName = helper.getView(R.id.child_name);
            if (listBean.isChildOpen())
                childName.setVisibility(View.VISIBLE);
            else
                childName.setVisibility(View.GONE);
            WebSettings settings = childName.getSettings();
            settings.setBuiltInZoomControls(true);
            childName.getSettings().setDefaultTextEncodingName("utf-8");
            String ip = DataCenter.getInstance().getIp();
            String demain = NetUtil.replaceIp2Domain(ip);
            String flag = "/fserver";
            String content = listBean.getHelpContent();
            content = content.replace(flag, demain + flag);
            childName.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");

            helper.addOnClickListener(R.id.parent_name);

        }
    }
}
