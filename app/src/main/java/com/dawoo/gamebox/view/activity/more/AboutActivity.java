package com.dawoo.gamebox.view.activity.more;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.AboutBean;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.view.IMineAboutView;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;

import butterknife.BindView;

/**
 * 关于我们
 * Created by jack on 18-3-22.
 */

public class AboutActivity extends BaseActivity implements IMineAboutView {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.htmlText)
    TextView htmlTexts;


    private CommonPresenter mPresenter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initViews() {
        headView.setHeader(getResources().getString(R.string.about_us), true);
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter(this, this);
        mPresenter.getAbout();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void onAboutResult(Object o) {
        if (o != null && o instanceof AboutBean) {
            AboutBean aboutBean = (AboutBean) o;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                htmlTexts.setText(Html.fromHtml(aboutBean.getContent(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                htmlTexts.setText(Html.fromHtml(aboutBean.getContent()));
            }
        }
    }
}
