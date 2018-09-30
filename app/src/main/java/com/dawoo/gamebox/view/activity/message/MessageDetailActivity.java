package com.dawoo.gamebox.view.activity.message;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.MessageDetail;
import com.dawoo.gamebox.bean.SiteMyMsgDetail;
import com.dawoo.gamebox.bean.SiteMyMsgDetailList;
import com.dawoo.gamebox.bean.SiteSysMsgDetail;
import com.dawoo.gamebox.mvp.presenter.MessagePresenter;
import com.dawoo.gamebox.mvp.view.IMessageDetailView;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MessageDetailActivity extends BaseActivity implements IMessageDetailView {
    public static final String TYPE_NOTICE = "TYPE_NOTICE";
    public static final String DETAIL_ID = "TYPE_ID";
    public static final String DETAIL_LINK = "Link";
    public static final int GAME_NOTICE = 0;
    public static final int SYS_NOTICE = 1;
    public static final int SITE_NOTICE_SYS = 2;
    public static final int SITE_NOTICE_MY = 3;
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.time1)
    TextView mTime1;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.ll_myMsg_reply)
    LinearLayout mLlMyMsgReply;

    private MessagePresenter mPresenter;
    private String mId;

    private String mLink;
    private String mTImeZone;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_message_detail);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        mTImeZone = SharePreferenceUtil.getTimeZone(this);
        mPresenter = new MessagePresenter(this, this);
        int type = getIntent().getIntExtra(TYPE_NOTICE, 0);
        mId = getIntent().getStringExtra(DETAIL_ID);
        if (GAME_NOTICE == type) {
            mHeadView.setHeader(getString(R.string.game_noitce), true);
            mTime1.setVisibility(View.GONE);
            mPresenter.getGameNoticeDetail(mId);
        } else if (SYS_NOTICE == type) {
            mHeadView.setHeader(getString(R.string.sys_notice), true);
            mTime1.setVisibility(View.GONE);
            mPresenter.getSysNoticeDetail(mId);
        } else if (SITE_NOTICE_SYS == type) {
            mHeadView.setHeader(getString(R.string.zhan_message), true);
            mTime.setVisibility(View.GONE);
            mPresenter.getSiteSysNoticeDetail(mId);
        } else if (SITE_NOTICE_MY == type) {
            mHeadView.setHeader(getString(R.string.zhan_message), true);
            mTime.setVisibility(View.GONE);
            mPresenter.advisoryMessageDetail(mId);
        }
    }


    @Override
    public void onLoadGameDetailResult(Object o) {
        if (o != null && o instanceof MessageDetail) {
            MessageDetail messageDetail = (MessageDetail) o;
            mContent.setText(Html.fromHtml(messageDetail.getContext()));
            mTime.setText(DateTool.convert2String(new Date(DateTool.changeTimeZone(messageDetail.getPublishTime(), mTImeZone, DateTool.getWuYong())), DateTool.FMT_DATE_TIME));
        }
    }

    @Override
    public void onLoadSysDetailResult(Object o) {
        if (o != null && o instanceof MessageDetail) {
            MessageDetail messageDetail = (MessageDetail) o;
            mTitle.setText(messageDetail.getTitle());
            mContent.setText(Html.fromHtml(messageDetail.getContent()));
            mTime.setText(DateTool.convert2String(new Date(DateTool.changeTimeZone(messageDetail.getPublishTime(), mTImeZone, DateTool.getWuYong())), DateTool.FMT_DATE_TIME));
        }
    }

    @Override
    public void onLoadSiteSysDetailResult(Object o) {
        if (o != null && o instanceof SiteSysMsgDetail) {
            SiteSysMsgDetail messageDetail = (SiteSysMsgDetail) o;
            mContent.setText(Html.fromHtml(messageDetail.getContent()));
            mTime1.setText(DateTool.convert2String(new Date(DateTool.changeTimeZone(messageDetail.getPublishTime(), mTImeZone, DateTool.getWuYong())), DateTool.FMT_DATE_TIME));
        }
    }

    @Override
    public void onLoadSiteMyDetailResult(Object o) {
        if (o != null && o instanceof SiteMyMsgDetailList) {
            SiteMyMsgDetailList messageDetaillist = (SiteMyMsgDetailList) o;
            if (messageDetaillist.getData() != null) {
                if (0 != messageDetaillist.getCode()) {
                    ToastUtil.showToastShort(this, "" + messageDetaillist.getMessage());
                    return;
                }
                mContent.setText(Html.fromHtml(messageDetaillist.getData().get(0).getAdvisoryContent()));
                mTime1.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME
                        , DateTool.changeTimeZone(messageDetaillist.getData().get(0).getAdvisoryTime(), mTImeZone, DateTool.getWuYong())));
                if (messageDetaillist.getData().get(0).getReplyList() != null) {
                    setMsgReplyData(messageDetaillist.getData().get(0).getReplyList());
                }
            }
        }
    }


    /**
     * 填充消息回复数据
     *
     * @param replyList
     */

    private void setMsgReplyData(List<SiteMyMsgDetail.ReplayListBean> replyList) {

        for (SiteMyMsgDetail.ReplayListBean replayListBean : replyList) {
            View view = this.getLayoutInflater().inflate(R.layout.activity_message_mymsg_reply, null);
            TextView repalyTime = view.findViewById(R.id.repalyTime);
            TextView replyTitle = view.findViewById(R.id.replyTitle);
            TextView replyContent = view.findViewById(R.id.replyContent);
            if (replayListBean.getReplyTitle() != null) {
                replyTitle.setText(replayListBean.getReplyTitle());
            }
            if (replayListBean.getReplyContent() != null) {
                replyContent.setText(replayListBean.getReplyContent());
            }
            repalyTime.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME, DateTool.changeTimeZone(replayListBean.getReplyTime(), mTImeZone, DateTool.getWuYong())));

            mLlMyMsgReply.addView(view);
        }
    }
}
