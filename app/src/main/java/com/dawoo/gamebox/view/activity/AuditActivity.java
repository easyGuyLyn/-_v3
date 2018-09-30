package com.dawoo.gamebox.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.adapter.HomeAdapter.AuditAdapter;
import com.dawoo.gamebox.bean.AuditBean;
import com.dawoo.gamebox.mvp.presenter.WithdrawPresenter;
import com.dawoo.gamebox.mvp.view.AuditView;
import com.dawoo.gamebox.view.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AuditActivity extends BaseActivity implements AuditView {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.head_view)
    HeaderView headView;

    WithdrawPresenter withdrawPresenter;
    AuditAdapter auditAdapter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_audit_list);
    }

    @Override
    protected void initViews() {
        headView.setHeader("查看稽核",true);
        withdrawPresenter = new WithdrawPresenter(this,this);
        LinearLayoutManager ms= new LinearLayoutManager(AuditActivity.this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为横向布局
        recycleView.setLayoutManager(ms);
    }

    @Override
    protected void initData() {
        withdrawPresenter.getAudit();
    }


    @Override
    public void getAuditState(Object o) {
        AuditBean auditBean = (AuditBean) o;
        if (!"0".equals(auditBean.getCode())){
            ToastUtil.showToastLong(this,auditBean.getMessage());
            return;
        }

        AuditBean.Data data = auditBean.getData();
        if (data!=null&&!"null".equals(data)){
            if (data.getWithdrawAudit()!=null&&!"null".equals(data.getWithdrawAudit())){
                auditAdapter = new AuditAdapter(this,R.layout.item_audit_text,data.getCurrencySign());
                auditAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null));
                recycleView.setAdapter(auditAdapter);
                auditAdapter.replaceData(data.getWithdrawAudit());
            }

        }

    }
}
