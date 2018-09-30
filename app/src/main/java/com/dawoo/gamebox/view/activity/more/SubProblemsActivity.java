package com.dawoo.gamebox.view.activity.more;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.CommentNextProblemBean;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.view.ISeconundTypeView;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jack on 18-3-22.
 */

public class SubProblemsActivity extends BaseActivity implements ISeconundTypeView {
    public static final String SEARCH_ID = "searchId";
    public static final String TITLE_NAME = "titleName";

    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    private String mSearchid;
    private String mTitleName;
    private SubProblemAdapter mProblemAdapter;
    private CommonPresenter mPresenter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_common_problem);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        mProblemAdapter = new SubProblemAdapter(R.layout.item_common_problem);
        mProblemAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null));
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mProblemAdapter);
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter(this, this);
        mSearchid = getIntent().getStringExtra(SEARCH_ID);
        mTitleName = getIntent().getStringExtra(TITLE_NAME);

        if (mTitleName != null) {
            mHeadView.setHeader(mTitleName, true);
        }
        if (mSearchid != null) {
            mPresenter.getSeconundType(mSearchid);
        }
    }


    @Override
    public void onResult(Object o) {
        if (o != null && o instanceof CommentNextProblemBean) {
            CommentNextProblemBean commentNextProblemBean = (CommentNextProblemBean) o;
            List<CommentNextProblemBean.ListBean> ListBean = commentNextProblemBean.getList();
            mProblemAdapter.setNewData(ListBean);
        }
    }

    class SubProblemAdapter extends BaseQuickAdapter {

        public SubProblemAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            CommentNextProblemBean.ListBean bean = (CommentNextProblemBean.ListBean) item;
            helper.setText(R.id.text_problem, bean.getName());
            helper.getView(R.id.text_problem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BoxApplication.getContext(), ProblemDetailActivity.class);
                    intent.putExtra(ProblemDetailActivity.SEARCH_ID, bean.getId());
                    startActivity(intent);
                }
            });
        }
    }
}
