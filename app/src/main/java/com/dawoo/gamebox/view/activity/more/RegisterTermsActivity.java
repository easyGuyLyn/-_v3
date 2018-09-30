package com.dawoo.gamebox.view.activity.more;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.MineTeamsBean;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.view.IMineTeamsView;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;

import butterknife.BindView;

/**
 * 注册条款
 * Created by jack on 18-3-25.
 */

public class RegisterTermsActivity extends BaseActivity implements IMineTeamsView {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.htmlText)
    TextView mHtmlTextsTv;
    private CommonPresenter mPresenter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initViews() {
        headView.setHeader(getResources().getString(R.string.registration_terms), true);
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter(this, this);
        mPresenter.getTears();
    }

    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void onResult(Object o) {
        if (o != null && o instanceof MineTeamsBean) {
            MineTeamsBean mineTeamsBean = (MineTeamsBean) o;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mHtmlTextsTv.setText(Html.fromHtml(mineTeamsBean.getValue(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                mHtmlTextsTv.setText(Html.fromHtml(mineTeamsBean.getValue()));
            }
        }
    }
}
