package com.dawoo.gamebox.view.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.NumberKeyListener;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.coretool.util.activity.KeyboardUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.payInfo.CounterBean;
import com.dawoo.gamebox.bean.payInfo.DepositBean;
import com.dawoo.gamebox.bean.payInfo.DepositResultBean;
import com.dawoo.gamebox.bean.payInfo.PayItemBean;
import com.dawoo.gamebox.bean.payInfo.PayTypeBean;
import com.dawoo.gamebox.bean.payInfo.PopPayBean;
import com.dawoo.gamebox.mvp.presenter.DisportPresenter;
import com.dawoo.gamebox.mvp.view.IDisportView;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.DepositUtil;
import com.dawoo.gamebox.util.FileTool;
import com.dawoo.gamebox.util.InputOrderEnum;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.PermissionUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.util.SpecialSiteEnum;
import com.dawoo.gamebox.util.StringTool;
import com.dawoo.gamebox.view.view.DepositSuccessDialog;
import com.dawoo.gamebox.view.view.HeaderView;
import com.dawoo.gamebox.view.view.PayNoticeDialog;
import com.hwangjr.rxbus.RxBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 存款二级页面
 *
 * @author rain
 * @date 18-3-22
 */

public class DisportActivity extends BaseActivity implements PayNoticeDialog.PostInfoInterface, IDisportView, HeaderView.LeftOnClickListener {
    @BindView(R.id.head_view)
    HeaderView titleView;
    @BindView(R.id.pay_image_iv)
    ImageView qrCodeView;

    @BindView(R.id.pay_image_rl)
    LinearLayout qrCodeRelayout;

    @BindView(R.id.bank_iv)
    ImageView bankIV;
    @BindView(R.id.bank_name_tv)
    TextView bankTV;
    @BindView(R.id.card_id_tv)
    TextView cardNumTV;
    @BindView(R.id.copy_id_tv)
    TextView copyNumTV;
    @BindView(R.id.open_name_tv)
    TextView nameTV;
    @BindView(R.id.bank_create_address_ll)
    LinearLayout bankCreateAddressLL;
    @BindView(R.id.bank_create_address_tv)
    TextView bankCreateAddressTv;
    @BindView(R.id.copy_bank_create_address_tv)
    TextView copyBankCreateAddressTv;
    @BindView(R.id.open_name_copy_tv)
    TextView copynameTV;
    @BindView(R.id.open_address_tv)
    TextView addressTV;
    @BindView(R.id.open_address_copy_tv)
    TextView copyAddressTV;
    @BindView(R.id.address_ll)
    RelativeLayout addressLayout;
    @BindView(R.id.start_pay_tv)
    TextView startApp;
    @BindView(R.id.save_pic_tv)
    TextView savePic;
    @BindView(R.id.remark_tv)
    TextView mRemarkTv;
    /**
     * 下半部
     */

    @BindView(R.id.first_name)
    TextView fName;
    @BindView(R.id.first_et)
    EditText fEdit;
    @BindView(R.id.sec_name)
    TextView sName;
    @BindView(R.id.sec_et)
    EditText sEdit;
    @BindView(R.id.third_name)
    TextView tName;
    @BindView(R.id.third_et)
    EditText tEdit;
    @BindView(R.id.third_rl)
    RelativeLayout tLayout;
    @BindView(R.id.sec_rl)
    RelativeLayout sLayout;
    @BindView(R.id.is_bank_tv)
    TextView isBankTV;
    @BindView(R.id.notice_tv)
    TextView noteTV;

    @BindView(R.id.counter_rl)
    RelativeLayout counter_rl;
    @BindView(R.id.count_sp)
    Spinner spinner;
    @BindView(R.id.rl_qrCdoe)
    RelativeLayout mRlQrCdoe;
    @BindView(R.id.count_tv)
    TextView mCountTv;
    @BindView(R.id.card_id_rl)
    RelativeLayout mCardIdRl;
    @BindView(R.id.name_ll)
    RelativeLayout mNameLl;
    @BindView(R.id.disport_bt)
    Button mDisportBt;


    private PayItemBean bean;
    private double money;
    private PopPayBean popPayBean;
    private PayNoticeDialog mDialog;
    private String code;
    private DisportPresenter mPresenter;
    private List<String> mArrayString = new ArrayList<>();
    private DepositSuccessDialog successDialog;
    private List<CounterBean> mCounters = new ArrayList<>();
    private boolean isHide;
    private String hideCode;
    String titleName = "";

    @Override
    protected void createLayoutView() {
        bean = (PayItemBean) getIntent().getSerializableExtra("item");
        money = getIntent().getDoubleExtra("money", 0.00);
        popPayBean = (PopPayBean) getIntent().getSerializableExtra("popPayBean");
        code = getIntent().getStringExtra("code");
        isHide = getIntent().getBooleanExtra("isHide", false);
        hideCode = getIntent().getStringExtra("hideCode");
        mCounters = (List<CounterBean>) getIntent().getSerializableExtra("counters");
        if (bean == null || money == 0 || popPayBean == null || code == null) {
            ToastUtil.showToastShort(this, getString(R.string.deposition_intent_err));
            finish();
            return;
        }
        setContentView(R.layout.activity_disport);
    }

    @Override
    protected void initViews() {
        mPresenter = new DisportPresenter(this, this);
        cardNumTV.setText(isHide ? hideCode : bean.getAccount());
        if (bean.getAliasName() == null || bean.getAliasName().isEmpty()) {
            bankTV.setText(bean.getPayName() + "");
        } else {
            bankTV.setText(bean.getAliasName() + "");
        }

        if (bean.getOpenAcountName() == null || bean.getOpenAcountName().isEmpty()) {
            addressLayout.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(bean.getCustomBankName())) {
                bankCreateAddressLL.setVisibility(View.VISIBLE);
                bankCreateAddressTv.setText(bean.getCustomBankName());
            } else {
                bankCreateAddressLL.setVisibility(View.GONE);
            }
        } else {
            addressLayout.setVisibility(View.VISIBLE);
            SpannableString mSpan = new SpannableString(getString(R.string.deposit_bank_address) + bean.getOpenAcountName());
            mSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 4, mSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            addressTV.setText(mSpan);
            isBankTV.setVisibility(View.VISIBLE);
        }
        SpannableStringBuilder mNameSpan = new SpannableStringBuilder(getString(R.string.name));

        if (bean.getFullName() != null) {
            mNameSpan.append(bean.getFullName());
        } else {
            mNameSpan.append(bean.getAliasName() + "");
        }
        mNameSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 3, mNameSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        nameTV.setText(mNameSpan);

        if (!TextUtils.isEmpty(code)) {
            if (code.equalsIgnoreCase(ConstantValue.QQ)) {
                startApp.setText("启动QQ支付");
            } else if (code.equalsIgnoreCase(ConstantValue.ALIPAY)) {
                startApp.setText("启动支付宝支付");
            } else if (code.equalsIgnoreCase(ConstantValue.WECHAT)) {
                startApp.setText("启动微信支付");
            } else if (code.equalsIgnoreCase(ConstantValue.JD)) {
                startApp.setText("启动京东钱包支付");
            } else if (code.equalsIgnoreCase(ConstantValue.BD)) {
                startApp.setText("启动百度钱包支付");
            } else if (code.equalsIgnoreCase(ConstantValue.ONECODEPAY)) {
                startApp.setVisibility(View.GONE);
            } else if (code.equalsIgnoreCase(ConstantValue.OTHER)) {
                startApp.setText("其他支付");
            } else {
                qrCodeRelayout.setVisibility(View.GONE);
            }
        } else {
            qrCodeRelayout.setVisibility(View.GONE);
        }

        if (isHide) {
            qrCodeRelayout.setVisibility(View.GONE);
        } else {
            if (bean.getQrCodeUrl() != null && !bean.getQrCodeUrl().isEmpty()) {
                savePic.setVisibility(View.VISIBLE);
                String url = NetUtil.handleUrl(bean.getQrCodeUrl());
                GlideApp.with(this).load(url).into(qrCodeView);
            } else {
                //               mRlQrCdoe.setVisibility(View.GONE);
//                savePic.setVisibility(View.GONE);
                //          qrCodeRelayout.setVisibility(View.GONE);
                qrCodeRelayout.setVisibility(View.GONE);
            }
        }

        String notes = DepositUtil.getNoteByCode(code, bean.isRandomAmount(), bean.getType(), false);
        notes = notes.replaceAll("<br>", "\n");
        SpannableStringBuilder mspan = new SpannableStringBuilder("温馨提示：\n" + notes);
        SpannableString colorSpan = new SpannableString("点击联系在线客服");
        colorSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
                RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.colorPrimary));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

        }, 0, colorSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mspan.append(colorSpan);
        noteTV.setText(mspan);
        noteTV.setMovementMethod(LinkMovementMethod.getInstance());
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.bitcoin);
        String url = NetUtil.handleUrl(bean.getImgUrl());
        GlideApp.with(this).load(url).apply(options).into(bankIV);

        // 设置前段备注
        if (!TextUtils.isEmpty(bean.getRemark())) {
            mRemarkTv.setText(bean.getRemark());
        }
    }

    @Override
    protected void initData() {

        switch (code) {
            case ConstantValue.COMPANY:
                titleName = getString(R.string.deposit_company_title);
                tLayout.setVisibility(View.GONE);
                sLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_company_sname));
                fEdit.setHint(getString(R.string.deposit_company_shint));

                break;
            case ConstantValue.WECHAT:
                titleName = getString(R.string.deposit_wechat_title);
                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_wechat_fname));
                fEdit.setHint(getString(R.string.deposit_wechat_fhint));

                sName.setText(getString(R.string.deposit_order_name));
                sEdit.setHint(getString(R.string.deposit_order_hint));
                sEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                sEdit.setTag("cannull");
                sEdit.setKeyListener(myKeyListener);
                startApp.setVisibility(View.VISIBLE);
                break;
            case ConstantValue.ALIPAY:
                titleName = getString(R.string.deposit_alipay_title);

                fName.setText(getString(R.string.deposit_alipay_fname));
                fEdit.setHint(getString(R.string.deposit_alipay_fhint));

                sName.setText(getString(R.string.deposit_alipay_sname));
                sEdit.setHint(getString(R.string.deposit_alipay_shint));

                tName.setText(getString(R.string.deposit_order_name));
                tEdit.setHint(getString(R.string.deposit_order_hint));
                tEdit.setTag("cannull");
                tEdit.setKeyListener(myKeyListener);
                tEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                startApp.setVisibility(View.VISIBLE);
                break;
            case ConstantValue.QQ:
                titleName = getString(R.string.deposit_qq_title);
                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_qq_fname));
                fEdit.setHint(getString(R.string.deposit_qq_fhint));
                fEdit.setInputType(InputType.TYPE_CLASS_NUMBER);

                sName.setText(getString(R.string.deposit_order_name));
                sEdit.setHint(getString(R.string.deposit_order_hint));
                sEdit.setTag("cannull");
                sEdit.setKeyListener(myKeyListener);
                sEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                startApp.setVisibility(View.VISIBLE);
                break;
            case ConstantValue.JD:
                titleName = getString(R.string.deposit_jd_title);

                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_jd_fname));
                fEdit.setHint(getString(R.string.deposit_jd_fhint));

                sName.setText(getString(R.string.deposit_order_name));
                sEdit.setHint(getString(R.string.deposit_order_hint));
                sEdit.setTag("cannull");
                sEdit.setKeyListener(myKeyListener);
                sEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                break;
            case ConstantValue.BD:
                titleName = getString(R.string.deposit_bd_title);
                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_bd_fname));
                fEdit.setHint(getString(R.string.deposit_bd_fhint));

                sName.setText(getString(R.string.deposit_order_name));
                sEdit.setHint(getString(R.string.deposit_order_hint));
                sEdit.setTag("cannull");
                sEdit.setKeyListener(myKeyListener);
                sEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                break;
            case ConstantValue.ONECODEPAY:
                //一码付
                titleName = getString(R.string.deposit_oncode_title);
                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_order_name));
                fEdit.setHint(getString(R.string.deposit_order_hint));
                fEdit.setTag("cannull");
                fEdit.setKeyListener(myKeyListener);
                fEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                sLayout.setVisibility(View.GONE);

                break;
            case ConstantValue.UNIONPAY:
                //银联扫码
                titleName = getString(R.string.deposit_company_title);
                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_order_name));
                fEdit.setHint(getString(R.string.deposit_order_hint));
                fEdit.setTag("cannull");
                fEdit.setKeyListener(myKeyListener);
                fEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                sLayout.setVisibility(View.GONE);
                break;
            case ConstantValue.COUNTER:
                //柜员机
                titleName = getString(R.string.deposit_counter_title);
                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_company_sname));
                fEdit.setHint(getString(R.string.deposit_company_shint));

                sName.setText(getString(R.string.deposit_counter_sname));
                sEdit.setHint(getString(R.string.deposit_counter_shint));
                sEdit.setTag("isaddress");
                sEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                counter_rl.setVisibility(View.VISIBLE);
                initSelector();
                break;
            default:
                //其他
                titleName = getString(R.string.deposit_other_title);
                tLayout.setVisibility(View.GONE);
                fName.setText(getString(R.string.deposit_other_fname));
                fEdit.setHint(getString(R.string.deposit_other_fhint));

                sName.setText(getString(R.string.deposit_order_name));
                sEdit.setHint(getString(R.string.deposit_order_hint));
                sEdit.setTag("cannull");
                sEdit.setKeyListener(myKeyListener);
                sEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                startApp.setVisibility(View.GONE);
                break;
        }
        if (!TextUtils.isEmpty(bean.getAccountInformation())){
            fName.setText(bean.getAccountInformation());
            fEdit.setHint(bean.getAccountPrompt());
        }
        titleView.setHeader(titleName, true);
        titleView.setmLeftBackListener(this);
    }


    @OnClick({R.id.disport_bt, R.id.copy_id_tv, R.id.copy_bank_create_address_tv, R.id.open_name_copy_tv, R.id.open_address_copy_tv, R.id.save_pic_tv, R.id.start_pay_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.disport_bt:
                showPop();
                break;
            case R.id.copy_bank_create_address_tv:
                copy(bean.getCustomBankName());
                break;
            case R.id.copy_id_tv:
                copy(bean.getAccount());
                break;
            case R.id.open_name_copy_tv:
                copy(bean.getFullName() == null ? bean.getAliasName() + "" : bean.getFullName() + "");
                break;
            case R.id.open_address_copy_tv:
                copy(bean.getOpenAcountName());
                break;
            case R.id.save_pic_tv:
                if (qrCodeView.getDrawable() == null) {
                    ToastUtil.showToastShort(this, "暂无二维码");
                    return;
                }
                qrCodeView.setDrawingCacheEnabled(true);
                if (!PermissionUtil.checkPermission(DisportActivity.this, ConstantValue.PERMISSIONS_STORAGE_WRITE, 1)) {
                    return;
                }
                Bitmap bitmap = qrCodeView.getDrawingCache();
                if (bitmap == null) {
                    ToastUtil.showToastShort(this, getString(R.string.deposit_uncatch_image_toast));
                    return;
                }
                FileTool.saveImageToGallery(this, bitmap);
                qrCodeView.destroyDrawingCache();
                break;
            case R.id.start_pay_tv:
                if (code.equalsIgnoreCase(ConstantValue.QQ)) {
                    ActivityUtil.startOtherapp(ConstantValue.packageQQ);
                } else if (code.equalsIgnoreCase(ConstantValue.ALIPAY)) {
                    ActivityUtil.startOtherapp(ConstantValue.packageali);
                } else if (code.equalsIgnoreCase(ConstantValue.WECHAT)) {
                    ActivityUtil.startOtherapp(ConstantValue.packagewechat);
                } else if (code.equalsIgnoreCase(ConstantValue.JD)) {
                    ActivityUtil.startOtherapp(ConstantValue.packagejd);
                } else if (code.equalsIgnoreCase(ConstantValue.BD)) {
                    ActivityUtil.startOtherapp(ConstantValue.packageBd);
                }
                break;
            default:
                break;
        }
    }


    void showPop() {
        String firstString = fEdit.getText().toString();
        String secString = sEdit.getText().toString();
        String thirdString = tEdit.getText().toString();
        /*
         * 119 270定制　　　　微信　支付宝支付需要订单号后五位必填
         */
//        if (mDialog == null) {
//            mDialog = new PayNoticeDialog(this);
//            mDialog.setSureOnlick(this);
//            mDialog.setData(money, popPayBean);
//        }
//
//        if (fEdit.getVisibility() == View.VISIBLE && firstString.isEmpty() && fEdit.getTag() == null) {
//            ToastUtil.showToastShort(this, fEdit.getHint().toString());
//            return;
//        } else if (!firstString.isEmpty() && firstString.length() < 5 && fEdit.getTag() != null) {
//            if ("cannull".equalsIgnoreCase(fEdit.getTag().toString())) {
//                ToastUtil.showToastShort(this, "请输入商户订单号后五位");
//                return;
//            }
//        }
//        if (((sLayout.getVisibility() == View.VISIBLE && sEdit.getTag() == null) || secString.isEmpty()) && secString != null) {
//
//            if (titleName.equals(getString(R.string.deposit_wechat_title))) {
//                ToastUtil.showToastShort(this, sEdit.getHint().toString());
//                return;
//            }
//
//        } else if (!secString.isEmpty() && secString.length() < 5 && sEdit.getTag() != null) {
//            if ("cannull".equalsIgnoreCase(sEdit.getTag().toString())) {
//                ToastUtil.showToastShort(this, "请输入商户订单号后五位");
//                return;
//            }
//        }
//
//        if ((tLayout.getVisibility() == View.VISIBLE && tEdit.getTag() == null) || thirdString.isEmpty()) {
//            if (titleName.equals(getString(R.string.deposit_alipay_title))) {
//                ToastUtil.showToastShort(this, tEdit.getHint().toString());
//                return;
//            }
//        } else if (!thirdString.isEmpty() && thirdString.length() < 5 && tEdit.getTag() != null) {
//            if ("cannull".equalsIgnoreCase(tEdit.getTag().toString())) {
//                ToastUtil.showToastShort(this, "请输入订单号后五位");
//                return;
//            }
//        }
//        if (code.equalsIgnoreCase(ConstantValue.COUNTER)) {
//            if (sEdit.length() > 20) {
//                ToastUtil.showToastShort(this, "请输入小于或等于20位地址");
//                return;
//            }
//        }
//        mDialog.show();

        if (mDialog == null) {
            mDialog = new PayNoticeDialog(this);
            mDialog.setSureOnlick(this);
            mDialog.setData(money, popPayBean);
        }
        // 判断是否为特殊站点　需要输入订单后５位
        String codeName = getBaseContext().getResources().getString(R.string.app_code);
        int codeId = InputOrderEnum.getCodeByCodeName(codeName);
        boolean isInputOrder = false;
        if (codeId == 0) {
            //不输入
            isInputOrder = false;
        } else {
            isInputOrder = true;
        }

        if (fEdit.getVisibility() == View.VISIBLE && firstString.isEmpty() && fEdit.getTag() == null) {
            ToastUtil.showToastShort(this, fEdit.getHint().toString());
            return;
        } else if (!firstString.isEmpty() && firstString.length() < 5 && fEdit.getTag() != null) {
            if (isInputOrder) {
                if ("cannull".equalsIgnoreCase(fEdit.getTag().toString())) {
                    ToastUtil.showToastShort(this, "请输入商户订单号后五位");
                    return;
                }
            }

        }
        if (((sLayout.getVisibility() == View.VISIBLE && sEdit.getTag() == null) || secString.isEmpty()) && secString != null) {

            if (titleName.equals(getString(R.string.deposit_wechat_title))) {
                if (isInputOrder) {
                    if (secString.isEmpty()) {
                        ToastUtil.showToastShort(this, sEdit.getHint().toString());
                        return;
                    }
                }
            }
            if (titleName.equals(getString(R.string.deposit_alipay_title))) {
                if (secString.isEmpty()) {
                    ToastUtil.showToastShort(this, sEdit.getHint().toString());
                    return;
                }
            }

        } else if (!secString.isEmpty() && secString.length() < 5 && sEdit.getTag() != null) {
            if (isInputOrder) {
                if ("cannull".equalsIgnoreCase(sEdit.getTag().toString())) {
                    ToastUtil.showToastShort(this, "请输入商户订单号后五位");
                    return;
                }
            }
        }

        if ((tLayout.getVisibility() == View.VISIBLE && tEdit.getTag() == null) || thirdString.isEmpty()) {
            if (titleName.equals(getString(R.string.deposit_alipay_title))) {
                if (isInputOrder) {
                    ToastUtil.showToastShort(this, tEdit.getHint().toString());
                    return;
                }
            }
        } else if (!thirdString.isEmpty() && thirdString.length() < 5 && tEdit.getTag() != null) {
            if (isInputOrder) {
                if ("cannull".equalsIgnoreCase(tEdit.getTag().toString())) {
                    ToastUtil.showToastShort(this, "请输入订单号后五位");
                    return;
                }
            }
        }

        if (code.equalsIgnoreCase(ConstantValue.COUNTER)) {
            if (sEdit.length() > 20) {
                ToastUtil.showToastShort(this, "请输入小于或等于20位地址");
                return;
            }
        }
        mDialog.show();
    }

    /**
     * 提交信息
     */
    @Override
    public void postPayInfo() {
        String firstString = fEdit.getText().toString().trim();
        String secString = sEdit.getText().toString().trim();
        String thirdString = tEdit.getText().toString().trim();
        long salesId = mDialog.getId();
        switch (code) {
            case ConstantValue.ONLINE:
                mPresenter.postOnline(money, bean.getRechargeType(), bean.getSearchId(), salesId, bean.getBankCode());
                break;
            case ConstantValue.COMPANY:
                if (firstString.isEmpty() && fEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, fEdit.getHint().toString());
                } else {
                    mPresenter.postCompany(money, bean.getRechargeType(), bean.getSearchId(), salesId, firstString, thirdString);
                }
                break;
            case ConstantValue.ALIPAY:
                if (firstString.isEmpty() && fEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, fEdit.getHint().toString());
                } else if (secString.isEmpty() && sEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, sEdit.getHint().toString());
                } else if (thirdString.isEmpty() && tEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, tEdit.getHint().toString());
                } else {
                    mPresenter.postElectronic(money, bean.getRechargeType(), bean.getSearchId(), salesId, thirdString, firstString, secString);
                }
                break;
            case ConstantValue.ONECODEPAY:
                //一码付
                if (firstString.isEmpty() && fEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, fEdit.getHint().toString());
                } else {
                    mPresenter.postElectronic(money, bean.getRechargeType(), bean.getSearchId(), salesId, firstString, "", "");
                }

                break;
            case ConstantValue.COUNTER:
                //柜员机
                if (firstString.isEmpty() && fEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, fEdit.getHint().toString());
                } else if (secString.isEmpty() && sEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, sEdit.getHint().toString());
                } else {
                    int selected = spinner.getSelectedItemPosition();
                    String counterCode;
                    if (mCounters == null || mCounters.isEmpty()) {
                        String[] codes = getResources().getStringArray(R.array.counter_spins_code);
                        counterCode = codes[selected];
                    } else {
                        counterCode = mCounters.get(selected).getCode();
                    }
                    mPresenter.postCompany(money, counterCode, bean.getSearchId(), salesId, firstString, secString);
                }
                break;
            default:
                //其他 jd qq wx bd
                if (firstString.isEmpty() && fEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, fEdit.getHint().toString().trim());
                } else if (secString.isEmpty() && sEdit.getTag() == null) {
                    ToastUtil.showToastShort(this, sEdit.getHint().toString().trim());
                } else {
                    mPresenter.postElectronic(money, bean.getRechargeType(), bean.getSearchId(), salesId, secString, thirdString, firstString);
                }
                break;
        }
    }

    /**
     * 柜员机加载
     */
    void initSelector() {
        if (mCounters == null || mCounters.isEmpty()) {
            mArrayString = Arrays.asList(getResources().getStringArray(R.array.counter_spins));
        } else {
            for (int i = 0; i < mCounters.size(); i++) {
                mArrayString.add(mCounters.get(i).getName());
            }
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.spinner_top_text_layout, mArrayString) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    //设置spinner展开的Item布局
                    convertView = getLayoutInflater().inflate(R.layout.spinner_item_layout, parent, false);
                }
                TextView spinnerText = convertView.findViewById(R.id.spinner_textView);
                spinnerText.setText(mArrayString.get(position));
                return convertView;
            }
        };
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(mArrayString.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(0, true);

    }

    /**
     * 复制功能
     *
     * @param content
     */

    public void copy(String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        ToastUtil.showToastShort(this, "成功复制到剪切板");
    }


    @Override
    public void getPayTypes(List<DepositBean> o) {

    }

    @Override
    public void getSecType(PayTypeBean o) {

    }


    @Override
    public void getPayInfo(PopPayBean o) {

    }

    /**
     * 获取信息提交后的信息
     *
     * @param o
     */
    @Override
    public void postPayInfo(DepositResultBean o) {
        if (o == null) {
            ToastUtil.showToastShort(this, "提交失败");
        } else {

            if (o.getPayLink() == null) {
                if (successDialog == null) {
                    successDialog = new DepositSuccessDialog(this);
                    successDialog.setCloseLinstener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            successDialog.dismiss();
                        }
                    });
                    successDialog.setSureLinstener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            successDialog.dismiss();
                            finish();

                        }
                    });
                    successDialog.setRechargeLinstener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            successDialog.dismiss();
                            startActivity(new Intent(DisportActivity.this, CapitalRecordActivity.class));
                            finish();
                        }
                    });
                }
                successDialog.show();
                return;
            }
            //   ActivityUtil.startWebView(o.getPayLink(), "", ConstantValue.WEBVIEW_TYPE_THIRD_ORDINARY, 1, "");
            if (TextUtils.isEmpty(o.getPayLink())) {
                SingleToast.showMsg("链接为空，请重试");
                return;
            }
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(o.getPayLink());
            intent.setData(content_url);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
        mPresenter.onDestory();
    }

    //字符过滤功能
    NumberKeyListener myKeyListener = new NumberKeyListener() {
        @Override
        public int getInputType() {
            //指定键盘类型
            return InputType.TYPE_CLASS_TEXT;
        }

        @Override
        protected char[] getAcceptedChars() {
            //指定你所接受的字符
            return ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-/:").toCharArray();
        }
    };

    @Override
    public void doBeforeFinish() {
        KeyboardUtil.hideInputKeyboard(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                savePic.callOnClick();
            } else {
                // Permission Denied
                ToastUtil.showToastShort(DisportActivity.this, "您没有授权存储权限，请在设置中打开授权");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
