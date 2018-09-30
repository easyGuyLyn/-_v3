package com.dawoo.gamebox.view.activity.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.ProblemBean;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.view.ICommonProblemView;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 常见问题
 * Created by jack on 18-3-22.
 */

public class ProblemsActivity extends BaseActivity implements ICommonProblemView {
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    private ProblemAdapter mProblemAdapter;
    private CommonPresenter mPresenter;


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_common_problem);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getResources().getString(R.string.common_problem), true);
        mProblemAdapter = new ProblemAdapter(R.layout.item_common_problem);
        mProblemAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null));
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mProblemAdapter);
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter(this, this);
        mPresenter.getCommentProblem();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    /**
     * 获取数据
     *
     * @param o
     */
    @Override
    public void onResult(Object o) {
        if (o != null && o instanceof List) {
            mProblemAdapter.setNewData((List) o);
        }
    }


    class ProblemAdapter extends BaseQuickAdapter {

        public ProblemAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            ProblemBean problemBean = (ProblemBean) item;
            helper.setText(R.id.text_problem, problemBean.getName());
            helper.getView(R.id.text_problem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProblemsActivity.this, SubProblemsActivity.class);
                    intent.putExtra(SubProblemsActivity.TITLE_NAME, problemBean.getName());
                    intent.putExtra(SubProblemsActivity.SEARCH_ID, problemBean.getId());
                    startActivity(intent);
                }
            });
        }
    }


}
