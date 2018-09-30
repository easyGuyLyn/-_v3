package com.dawoo.gamebox.util.line;

/**
 * archar  天纵神武
 **/
public class LineProgressString {
    /**
     * code
     */
    public static final String CODE_001 = "001";//当阿里云连接失败时，返回错误代码001;
    public static final String CODE_002 = "002";//当boss-api请求失败，返回错误代码002;
    public static final String CODE_003 = "003";//当check不通过，返回003，并将check的url地址输出;

    /**
     * string
     */
    public static final String LINE_RESULT_GET_ALIYUN_FAILURE = "线路服务器获取失败";

    public static final String LINE_RESULT_GET_FAILURE = "线路获取失败";

    public static final String LINE_RESULT_RETURN_EMPTY = "线路返回为空";

    public static final String LINE_RESULT_RETURN_IPS_EMPTY = "ip返回为空";

    public static final String LINE_RESULT_RETURN_EXCEPTION = "线路返回异常";

    public static final String LINE_RESULT_CHECK_IP_UNPASS = "ip检测未通过";

    /**
     * ------------------------------------------------------------------
     */
    public static final String LINE_PROGRESS_GETTING_BASE_LINE = "正在连接线路服务器,请稍后...";

    public static final String LINE_PROGRESS_GETTING_LINE = "正在获取线路,请稍后...";

    public static final String LINE_PROGRESS_SETTING_PORT = "正在确定线路端口,请稍后...";

    public static final String LINE_PROGRESS_CHECKING_LINE = "正在检测线路,请稍后...";

    public static final String LINE_PROGRESS_CONNECTING = "匹配服务器成功,正在连接...";

    public static final String LINE_PROGRESS_CONNECTED = "已连接";

}
