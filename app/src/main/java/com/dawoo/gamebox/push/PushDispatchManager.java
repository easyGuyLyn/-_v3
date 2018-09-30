package com.dawoo.gamebox.push;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dawoo.gamebox.bean.push.NoticeMessage;
import com.dawoo.gamebox.push.type.Topics;
import com.hwangjr.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 推送消息分发器
 * archar
 */
public class PushDispatchManager {

    public static final String EVENT_PUSH_MSG_CENTER = "event_msg_center";//消息中心的推送

    private static PushDispatchManager mInstance;

    public static PushDispatchManager getInstance() {
        if (mInstance == null) {
            synchronized (PushDispatchManager.class) {
                if (mInstance == null) {
                    mInstance = new PushDispatchManager();
                }
            }
        }
        return mInstance;
    }

    public void disPatch(String json) {
        if (json == null) return;
        JSONArray jsonArray = JSONObject.parseArray(json);
        List<NoticeMessage> noticeMessages = new ArrayList<>();
        for (Object job : jsonArray) {
            JSONObject jobj = JSONObject.parseObject(job.toString());
            NoticeMessage noticeMessage = JSONObject.toJavaObject(jobj, NoticeMessage.class);
            noticeMessages.add(noticeMessage);
        }
        for (NoticeMessage noticeMessage : noticeMessages) {
            String subscribeType = noticeMessage.getSubscribeType();
            if (TextUtils.isEmpty(subscribeType)) return;
            Log.e("disPatch", subscribeType);
            switch (subscribeType) {
                case Topics.SYS_ANN:
                    RxBus.get().post(EVENT_PUSH_MSG_CENTER, "");
                    break;
                case Topics.SITE_ANN:

                    break;
                case Topics.MSITE_Player_Announcement_Notice:

                    break;
                case Topics.MCENTER_READ_COUNT:
                    RxBus.get().post(EVENT_PUSH_MSG_CENTER, "");
                    break;
                case Topics.MSITE_ONLINERECHARGE:

                    break;
                case Topics.MSITE_DIGICCY_REFRESH_BALANCE:

                    break;

            }
        }

    }

}
