package com.dawoo.gamebox.view.activity.message;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.ResetSecurityPwd;
import com.dawoo.gamebox.bean.SiteMsgType;
import com.dawoo.gamebox.mvp.presenter.MessagePresenter;
import com.dawoo.gamebox.mvp.view.ISiteUploadMsgView;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.view.fragment.BaseFragment;
import com.dawoo.gamebox.view.view.CustomPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Archar on 2018
 */
public class SiteUploadMsgFragment extends BaseFragment implements ISiteUploadMsgView {

    Unbinder unbinder;
    @BindView(R.id.tv_site_sendMsg_type)
    TextView mTvSiteSendMsgType;
    @BindView(R.id.et_set_title)
    EditText mEtSetTitle;
    @BindView(R.id.et_site_sendMsg)
    EditText mEtSiteSendMsg;
    @BindView(R.id.btn_msg_upload)
    Button mBtnMsgUpload;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.etCaptcha)
    EditText mEtCaptcha;
    @BindView(R.id.ivCaptcha)
    ImageView mIvCaptcha;
    @BindView(R.id.rlCaptcha)
    RelativeLayout mRlCaptcha;
    private MessagePresenter mPresneter;
    private CustomPopupWindow mTypePopupWindow;
    private String mTransactionType = "";//
    private List<String> mTypeList = new ArrayList<>();
    private Map<String, String> mTypesMap = new ArrayMap<>(); //存放类型的map,中文-英文,传参要英文

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_site_send_msg_layout, container, false);
        unbinder = ButterKnife.bind(this, v);
        initView();
        initData();
        return v;
    }

    private void initData() {
        mPresneter = new MessagePresenter(getActivity(), this);
        loadData();
    }

    private void initView() {
    }


    @Override
    protected void loadData() {
        mPresneter.getSiteMsgType();
    }

    @Override
    public void onDestroyView() {
        mPresneter.onDestory();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onGetMsgType(Object o) {

        SiteMsgType siteMsgType = (SiteMsgType) o;
        if (siteMsgType.isOpenCaptcha()) {
            mRlCaptcha.setVisibility(View.VISIBLE);
            getCaptcha(siteMsgType.getCaptcha_value());
        }
        if (siteMsgType == null || siteMsgType.getAdvisoryTypeList().size() == 0) return;
        mTypeList.clear();
        mTypesMap.clear();
        for (int i = 0; i < siteMsgType.getAdvisoryTypeList().size(); i++) {
            mTypeList.add(siteMsgType.getAdvisoryTypeList().get(i).getAdvisoryName());
            mTypesMap.put(siteMsgType.getAdvisoryTypeList().get(i).getAdvisoryName(), siteMsgType.getAdvisoryTypeList().get(i).getAdvisoryType());
        }
        initOpenTypeChoosePopup(mTypeList);

    }


    @Override
    public void onUploadResult(Object o) {
        ResetSecurityPwd resetSecurityPwd = (ResetSecurityPwd) o;

        if (resetSecurityPwd.getCode().equals("0")) {
            ToastUtil.showResShort(getActivity(), R.string.action_success);
            mTvSiteSendMsgType.setText(getString(R.string.please_choose));
            mEtSetTitle.setText("");
            mEtSiteSendMsg.setText("");
            mEtCaptcha.setText("");

        } else {
            ToastUtil.showToastShort(getActivity(), resetSecurityPwd.getMessage());
        }
        if (mRlCaptcha.getVisibility() == View.VISIBLE) {
            mPresneter.getSiteMsgType();
        }

        if (resetSecurityPwd.getData() != null && resetSecurityPwd.getData().getIsOpenCaptcha() != null) {
            if (resetSecurityPwd.getData().getIsOpenCaptcha().equals("true")) {
                mPresneter.getSiteMsgType();
            }
        }
        if (resetSecurityPwd.getCode().equals("1001")) {
            ActivityUtil.gotoLogin();
        }
    }

    /**
     * 创建类型选择
     */
    private void initOpenTypeChoosePopup(List<String> list) {
        mTypePopupWindow = new CustomPopupWindow(getActivity(), new TypeChooseAdapter(R.layout.custom_popup_list_item_view, list));
    }

    @OnClick(R.id.ivCaptcha)
    public void onViewClicked() {
        mPresneter.getSiteMsgType();
    }

    public class TypeChooseAdapter extends BaseQuickAdapter {

        public TypeChooseAdapter(int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            helper.setText(R.id.item_tv, String.valueOf(item));
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = helper.getAdapterPosition();
                    String typeName = (String) TypeChooseAdapter.this.mData.get(position);
                    mTransactionType = mTypesMap.get(typeName);
                    mTvSiteSendMsgType.setText(typeName);
                    mTypePopupWindow.dissMissPopWindow();
                }
            });
        }
    }


    @OnClick({R.id.btn_msg_upload, R.id.btn_cancel, R.id.tv_site_sendMsg_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_msg_upload:
                String advisoryType = mTransactionType;
                String advisoryTitle = mEtSetTitle.getText().toString();
                String advisoryContent = mEtSiteSendMsg.getText().toString();
                String code = mEtCaptcha.getText().toString();
                if (checkInput(advisoryType, advisoryTitle, advisoryContent)) {
                    if (TextUtils.isEmpty(code)) {
                        mPresneter.addNoticeSite(advisoryType, advisoryTitle, advisoryContent);
                    } else {
                        mPresneter.addNoticeSite(advisoryType, advisoryTitle, advisoryContent, code);
                    }
                }
                break;
            case R.id.btn_cancel:
                mTvSiteSendMsgType.setText(getString(R.string.please_choose));
                mEtSetTitle.setText("");
                mEtSiteSendMsg.setText("");
                mEtCaptcha.setText("");
                break;
            case R.id.tv_site_sendMsg_type:
                if (mTypePopupWindow == null) return;
                mTypePopupWindow.doTogglePopupWindow(mTvSiteSendMsgType);
                break;
        }
    }

    private Boolean checkInput(String advisoryType, String advisoryTitle, String advisoryContent) {
        if (TextUtils.isEmpty(advisoryType)) {
            ToastUtil.showToastShort(getActivity(), getString(R.string.input_type_notnull));
            return false;

        }
        if (TextUtils.isEmpty(advisoryTitle)) {
            ToastUtil.showToastShort(getActivity(), getString(R.string.input_title_notnull));
            return false;
        }

        if (TextUtils.isEmpty(advisoryContent)) {
            ToastUtil.showToastShort(getActivity(), getString(R.string.input_cotent_notnull));
            return false;
        }
        if (advisoryTitle.length() < 4) {
            ToastUtil.showToastShort(getActivity(), getString(R.string.input_title_4));
            return false;
        }
        if (advisoryContent.length() < 10) {
            ToastUtil.showToastShort(getActivity(), getString(R.string.input_content_10));
            return false;
        }

        return true;
    }

    //获取验证码
    private void getCaptcha(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mIvCaptcha.setImageBitmap(null);
                        }
                    });
                }

                OkHttpUtils.get().url(DataCenter.getInstance().getIp() + url)
                        .addParams("_t", String.valueOf(new Date().getTime()))
                        .headers(NetUtil.setHeaders()).build().execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("captcha error ==> " + e.getMessage());
                        if (getActivity() == null) return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showResShort(getActivity(), R.string.getCaptchaFail);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        if (getActivity() == null) return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mIvCaptcha != null) {
                                    mIvCaptcha.setImageBitmap(response);
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

}
