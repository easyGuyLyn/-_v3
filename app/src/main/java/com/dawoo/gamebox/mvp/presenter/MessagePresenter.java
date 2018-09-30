package com.dawoo.gamebox.mvp.presenter;

import android.content.Context;

import com.dawoo.gamebox.mvp.model.message.MessageModel;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IMessageDetailView;
import com.dawoo.gamebox.mvp.view.IGameNoticeView;
import com.dawoo.gamebox.mvp.view.ISiteMsgView;
import com.dawoo.gamebox.mvp.view.ISiteSysNoticeView;
import com.dawoo.gamebox.mvp.view.ISiteUploadMsgView;
import com.dawoo.gamebox.mvp.view.ISysNoticeView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;

import rx.Subscription;

/**
 * 消息类
 * 游戏公告
 * 游戏公告详情
 * 系统公告
 * 系统公告详情
 * 站点消息
 * Created by benson on 18-1-15.
 */

public class MessagePresenter<T extends IBaseView> extends BasePresenter {
    private final Context mContext;
    private T mView;
    private final MessageModel mModel;


    public MessagePresenter(Context context, T mView) {
        super(context, mView);

        mContext = context;
        this.mView = mView;
        mModel = new MessageModel();
    }


    /**
     * 获取游戏公告
     */
    public void getGameNotice(String startTime, String endTime, int pageNumber, int pageSize, Integer apiId) {
        Subscription subscription = mModel.getGameNotice(new ProgressSubscriber(o -> ((IGameNoticeView) mView).onLoadResult(o), mContext),
                startTime,
                endTime,
                pageNumber,
                pageSize,
                apiId);
        subList.add(subscription);
    }

    public void loadMoreGameNotice(String startTime, String endTime, int pageNumber, int pageSize, Integer apiId) {
        Subscription subscription;

//        if (0 == apiId) {
//            subscription = mModel.getGameNotice(new ProgressSubscriber(o -> ((IGameNoticeView) mView).onLoadMoreResult(o), mContext),
//                    startTime,
//                    endTime,
//                    pageNumber,
//                    pageSize);
//        } else {
//
//        }

        subscription = mModel.getGameNotice(new ProgressSubscriber(o -> ((IGameNoticeView) mView).onLoadMoreResult(o), mContext),
                startTime,
                endTime,
                pageNumber,
                pageSize,
                apiId);

        subList.add(subscription);
    }


    /**
     * 获取游戏公告
     */
    public void getGameNotice(int pageNumber, int pageSize) {
        Subscription subscription = mModel.getGameNotice(new ProgressSubscriber(o ->
                        ((IGameNoticeView) mView).onLoadResult(o), mContext),
                pageNumber,
                pageSize);
        subList.add(subscription);
    }

    public void loadMoreGameNotice(int pageNumber, int pageSize) {
        Subscription subscription = mModel.getGameNotice(new ProgressSubscriber(o ->
                        ((IGameNoticeView) mView).onLoadMoreResult(o), mContext),
                pageNumber,
                pageSize);
        subList.add(subscription);
    }

    public void getGameNoticeDetail(String searchId) {
        Subscription subscription = mModel.getGameNoticeDetail(new ProgressSubscriber(o ->
                        ((IMessageDetailView) mView).onLoadGameDetailResult(o), mContext),
                searchId);
        subList.add(subscription);
    }

    public void getSysNotice(int pageNumber, int pageSize) {
        Subscription subscription = mModel.getSysNotice(new ProgressSubscriber(o ->
                        ((ISysNoticeView) mView).onLoadResult(o), mContext),
                pageNumber,
                pageSize);
        subList.add(subscription);
    }

    public void getSysNotice(String startTime, String endTime, int pageNumber, int pageSize) {
        Subscription subscription = mModel.getSysNotice(new ProgressSubscriber(o -> ((ISysNoticeView) mView).onLoadResult(o), mContext),
                startTime,
                endTime,
                pageNumber,
                pageSize);
        subList.add(subscription);
    }

    public void loadMoreSysNotice(String startTime, String endTime, int pageNumber, int pageSize) {
        Subscription subscription = mModel.getSysNotice(new ProgressSubscriber(o -> ((ISysNoticeView) mView).onLoadMoreResult(o), mContext),
                startTime,
                endTime,
                pageNumber,
                pageSize);
        subList.add(subscription);
    }

    public void getSysNoticeDetail(String searchId) {
        Subscription subscription = mModel.getSysNoticeDetail(new ProgressSubscriber(o ->
                        ((IMessageDetailView) mView).onLoadSysDetailResult(o), mContext),
                searchId);
        subList.add(subscription);
    }

    /**
     * 获取站点---系统消息
     */

    public void getSiteSysMsg(int pageNumber, int pageSize, Boolean isLoadMore) {
        Subscription subscription = null;
        if (isLoadMore) {
            subscription = mModel.getSiteSysNotice(new ProgressSubscriber(o ->
                            ((ISiteSysNoticeView) mView).onLoadMoreResult(o), mContext),
                    pageNumber,
                    pageSize);
        } else {
            subscription = mModel.getSiteSysNotice(new ProgressSubscriber(o ->
                            ((ISiteSysNoticeView) mView).onLoadResult(o), mContext),
                    pageNumber,
                    pageSize);
        }

        subList.add(subscription);
    }

    /**
     * 站点---标记已读
     */
    public void setSiteSysNoticeStatus(String ids) {
        Subscription subscription = mModel.setSiteSysNoticeStatus(new ProgressSubscriber(o ->
                        ((ISiteSysNoticeView) mView).onReadMsgsResult(o), mContext),
                ids);
        subList.add(subscription);
    }

    public void setSiteSysNoticeReadStatus(String ids) {
        Subscription subscription = mModel.setSiteSysNoticeStatus(new ProgressSubscriber(o ->
                        ((ISiteSysNoticeView) mView).onReadMsgResult(o), mContext, false),
                ids);
        subList.add(subscription);
    }

    /**
     * 站点---删除消息
     */
    public void deleteSiteSysNotice(String ids) {
        Subscription subscription = mModel.deleteSiteSysNotice(new ProgressSubscriber(o ->
                        ((ISiteSysNoticeView) mView).onDeleteMsgsResult(o), mContext),
                ids);
        subList.add(subscription);
    }


    /**
     * 获取站点---我的消息
     */

    public void getSiteMyMsg(int pageNumber, int pageSize, Boolean isLoadMore) {
        Subscription subscription = null;
        if (isLoadMore) {
            subscription = mModel.getSiteMyNotice(new ProgressSubscriber(o ->
                            ((ISiteSysNoticeView) mView).onLoadMoreResult(o), mContext),
                    pageNumber,
                    pageSize);
        } else {
            subscription = mModel.getSiteMyNotice(new ProgressSubscriber(o ->
                            ((ISiteSysNoticeView) mView).onLoadResult(o), mContext),
                    pageNumber,
                    pageSize);
        }

        subList.add(subscription);
    }

    /**
     * 站点---标记已读 我的
     */
    public void setSiteMyNoticeStatus(String ids) {
        Subscription subscription = mModel.setSiteMyNoticeStatus(new ProgressSubscriber(o ->
                        ((ISiteSysNoticeView) mView).onReadMsgsResult(o), mContext),
                ids);
        subList.add(subscription);
    }

    public void setSiteMyNoticeReadStatus(String ids) {
        Subscription subscription = mModel.setSiteMyNoticeStatus(new ProgressSubscriber(o ->
                        ((ISiteSysNoticeView) mView).onReadMsgResult(o), mContext),
                ids);
        subList.add(subscription);
    }

    /**
     * 站点---删除消息  我的
     */
    public void deleteSiteMyNotice(String ids) {
        Subscription subscription = mModel.deleteSiteMyNotice(new ProgressSubscriber(o ->
                        ((ISiteSysNoticeView) mView).onDeleteMsgsResult(o), mContext),
                ids);
        subList.add(subscription);
    }

    /**
     * 站点---发送消息  类型
     */
    public void getSiteMsgType() {
        Subscription subscription = mModel.getSiteMsgType(new ProgressSubscriber(o ->
                ((ISiteUploadMsgView) mView).onGetMsgType(o), mContext));
        subList.add(subscription);
    }


    /**
     * 发送消息
     */
    public void addNoticeSite(String advisoryType, String advisoryTitle, String advisoryContent) {
        Subscription subscription = mModel.addNoticeSite(new ProgressSubscriber(o ->
                        ((ISiteUploadMsgView) mView).onUploadResult(o), mContext), advisoryType
                , advisoryTitle, advisoryContent);
        subList.add(subscription);
    }


    /**
     * 发送消息
     */
    public void addNoticeSite(String advisoryType, String advisoryTitle, String advisoryContent, String code) {
        Subscription subscription = mModel.addNoticeSite(new ProgressSubscriber(o ->
                        ((ISiteUploadMsgView) mView).onUploadResult(o), mContext), advisoryType
                , advisoryTitle, advisoryContent, code);
        subList.add(subscription);
    }

    /**
     * 站点---未读msg count
     */
    public void getUnReadMsgCount() {
        Subscription subscription = mModel.getUnReadMsgCount(new ProgressSubscriber(o ->
                ((ISiteMsgView) mView).onGetUnReadCountResult(o), mContext, false));
        subList.add(subscription);
    }

    public void getSiteSysNoticeDetail(String searchId) {
        Subscription subscription = mModel.getSiteSysNoticeDetail(new ProgressSubscriber(o ->
                        ((IMessageDetailView) mView).onLoadSiteSysDetailResult(o), mContext),
                searchId);
        subList.add(subscription);
    }


    public void advisoryMessageDetail(String id) {
        Subscription subscription = mModel.advisoryMessageDetail(new ProgressSubscriber(o ->
                        ((IMessageDetailView) mView).onLoadSiteMyDetailResult(o), mContext),
                id);
        subList.add(subscription);
    }


    @Override
    public void onDestory() {
        super.onDestory();
    }
}
