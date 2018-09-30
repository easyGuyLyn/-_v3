package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.Banner;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 广告弹窗
 * Created by benson on 18-1-1.
 */

public class PhoneDialog {
    private final Context mContext;
    private final List<Banner.PhoneDialogBean> mNoticeList;
    @BindView(R.id.close_iv)
    ImageView mClose;
    @BindView(R.id.notice_dialog_recyclerview)
    RecyclerView noticeRecyclerView;
    private Dialog mDialog;
    @BindView(R.id.notice_title)
    TextView noticeTitle;
    @BindView(R.id.confirm_btn)
    Button mConfirmBtn;
    private String mLink;


    public PhoneDialog(@NonNull Context context, List<Banner.PhoneDialogBean> list) {
        mContext = context;
        mNoticeList = list;
        mDialog = new Dialog(context, R.style.CustomDialogStyle);
        mDialog.setContentView(R.layout.phone_diaolog);
        mDialog.setCancelable(false);
        ButterKnife.bind(this, mDialog);
        setLayoutParams();
        initViews();
        mDialog.show();
    }

    private void setLayoutParams() {
        Window win = mDialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @OnClick({R.id.close_iv, R.id.confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                dismissDialog();
                break;
            case R.id.confirm_btn:
                if (TextUtils.isEmpty(mLink)) {
                    dismissDialog();
                } else {
                    ActivityUtil.startWebView(NetUtil.handleUrl(mLink), "", ConstantValue.WEBVIEW_TYPE_ORDINARY,1,"");
                }
                break;
        }
    }

    void initViews() {
        NoticeRecyclerAdapter mAdapter = new NoticeRecyclerAdapter();
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.setAdapter(mAdapter);
        //设置分割线
        noticeRecyclerView.addItemDecoration(new DashlineItemDivider());
    }


    class NoticeRecyclerAdapter extends RecyclerView.Adapter {


        public NoticeRecyclerAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NoticeViewHolder noticeViewHolder = new NoticeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_phone_dialog_recycler, parent, false));
            return noticeViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Banner.PhoneDialogBean bean = mNoticeList.get(position);
            NoticeViewHolder viewHolder = (NoticeViewHolder) holder;
            setTitleName(bean.getName());
            if ("1".equals(bean.getContent_type())) { // 1是图片 其他是文字
                String url = bean.getCover();
                if (!url.contains("http")) {
                    url = NetUtil.handleUrl(url);
                }
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher);
                GlideApp.with(mContext).load(url).apply(options).into(viewHolder.phoneItemIv);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    viewHolder.phoneItemTv.setText(Html.fromHtml(bean.getContent(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    viewHolder.phoneItemTv.setText(Html.fromHtml(bean.getContent()));
                }
            }
            mLink = bean.getLink();
        }

        @Override
        public int getItemCount() {
            return mNoticeList.size();
        }
    }


    class NoticeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.phone_dialog_tv)
        TextView phoneItemTv;
        @BindView(R.id.phone_dialog_iv)
        ImageView phoneItemIv;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DashlineItemDivider extends RecyclerView.ItemDecoration {


        public void onDrawOver(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < (childCount - 1); i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                //以下计算主要用来确定绘制的位置
                final int top = child.getBottom() + params.bottomMargin;

                //绘制虚线
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(ContextCompat.getColor(mContext, R.color.setting_divide_line));
                Path path = new Path();
                path.moveTo(left, top);
                path.lineTo(right, top);
                PathEffect effects = new DashPathEffect(new float[]{15, 15, 15, 15}, 5);//此处单位是像素不是dp  注意 请自行转化为dp
                paint.setPathEffect(effects);
                c.drawPath(path, paint);


            }
        }

    }

    /**
     * 设置titleName
     *
     * @param titleName
     */
    public void setTitleName(String titleName) {
        noticeTitle.setText(titleName);
    }

    /**
     * 设置颜色
     */
    public void setTextColor(int color) {
        noticeTitle.setTextColor(color);
    }

}
