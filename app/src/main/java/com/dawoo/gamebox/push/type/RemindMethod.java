package com.dawoo.gamebox.push.type;

/**
 * archar
 */
public enum RemindMethod {

    NUM("NUM", "仅显示消息数量"),                                          //app只在站内信出现，在主页面显示数量，无需弹窗
    NUM_POPUP("NUM_POPUP", "显示消息数量并弹窗"),                          //app收到后需要弹窗
    NUM_SOUND("NUM_SOUND", "显示消息数量并播放声音"),                        //app收到后需要弹窗，暂时不播放声音
    NUM_POPUP_SOUND("NUM_POPUP_SOUND", "显示消息数量且弹窗并播放声音"),       //app收到后需要弹窗，暂时不播放声音
    TASK_NUM("NUM", "仅显示任务数量"),                                    //app只在站内信出现，在主页面显示数量，无需弹窗
    TASK_NUM_POPUP("NUM_POPUP", "显示任务数量并弹窗"),                     //app收到后需要弹窗
    TASK_NUM_SOUND("NUM_SOUND", "显示任务数量并播放声音"),                   //app只在站内信出现，在主页面显示数量，无需弹窗
    TASK_NUM_POPUP_SOUND("NUM_POPUP_SOUND", "显示任务数量且弹窗并播放声音");  //app收到后需要弹窗

    private String code;
    private String name;

    RemindMethod(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
