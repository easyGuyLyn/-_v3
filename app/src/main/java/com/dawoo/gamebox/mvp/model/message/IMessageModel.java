package com.dawoo.gamebox.mvp.model.message;

import retrofit2.http.Field;
import rx.Subscriber;
import rx.Subscription;

/**
 * 消息
 * Created by benson on 18-1-15.
 */

public interface IMessageModel {
    Subscription getGameNotice(Subscriber subscriber, String startTime, String endTime, int pageNumber, int pageSize, Integer apiId);

    Subscription getGameNotice(Subscriber subscriber, String startTime, String endTime, int pageNumber, int pageSize);

    Subscription getGameNotice(Subscriber subscriber, int pageNumber, int pageSize);

    Subscription getSysNotice(Subscriber subscriber, String startTime, String endTime, int pageNumber, int pageSize);

    Subscription getSysNotice(Subscriber subscriber, int pageNumber, int pageSize);

    Subscription getGameNoticeDetail(Subscriber subscriber, String searchId);

    Subscription getSysNoticeDetail(Subscriber subscriber, String searchId);

    Subscription getSiteSysNotice(Subscriber subscriber, int pageNumber, int pageSize);

    Subscription setSiteSysNoticeStatus(Subscriber subscriber, String ids);

    Subscription deleteSiteSysNotice(Subscriber subscriber, String ids);

    Subscription getSiteMyNotice(Subscriber subscriber, int pageNumber, int pageSize);

    Subscription setSiteMyNoticeStatus(Subscriber subscriber, String ids);

    Subscription deleteSiteMyNotice(Subscriber subscriber, String ids);

    Subscription getSiteMsgType(Subscriber subscriber);

    Subscription addNoticeSite(Subscriber subscriber, String advisoryType, String advisoryTitle, String advisoryContent);

    Subscription addNoticeSite(Subscriber subscriber, String advisoryType, String advisoryTitle, String advisoryContent, String code);

    Subscription getUnReadMsgCount(Subscriber subscriber);

    Subscription getSiteSysNoticeDetail(Subscriber subscriber, String searchId);

    Subscription advisoryMessageDetail(Subscriber subscriber, String id);

}
