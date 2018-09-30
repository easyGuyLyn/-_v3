package com.dawoo.gamebox;

/**
 * 常量
 */

public interface ConstantValue {

    /**
     * 是否直连开发环境
     */
    Boolean DEVELOPE = false;

    /**
     * 21 测试环境 code码
     */
    String TEST_APP_CODE = "wjbn";

    /**
     * 获取公网ip 地址
     */
    //String IP_LOCAL_URL = "http://ifconfig.co/json";
    String IP_LOCAL_URL = "http://ip.chinaz.com/getip.aspx";


    /**
     * 阿里云总线路服务器
     */
    String BASE_URL_1_IP = "http://203.107.1.33/194768/d?host=apiplay.info";
    String BASE_URL_2_IP = "http://203.107.1.33/194768/d?host=hpdbtopgolddesign.com";
    String BASE_URL_3_IP = "http://203.107.1.33/194768/d?host=agpicdance.info";

    /**
     * 线路域名URL
     */
    String BOSS_ = ":1344/boss";

    String BOSS_API = "/boss-api";

    String BOSS_API_1 = ":1344/boss-api";

    String BASE_URL_HEAD = "https://";

    String LINE_URL = "/app/line.html";

    String BASE_URL = BASE_URL_HEAD + "apiplay.info" + BOSS_;
    String BASE_URL_1 = BASE_URL_HEAD + "apiplay.info" + BOSS_API_1;
    String BASE_URL_2 = BASE_URL_HEAD + "agpicdance.info" + BOSS_API_1;
    String BASE_URL_3 = BASE_URL_HEAD + "hpdbtopgolddesign.com" + BOSS_API_1;
    String[] fecthUrl = {BASE_URL_1 + LINE_URL, BASE_URL_2 + LINE_URL, BASE_URL_3 + LINE_URL};

    String BASE_URL_4 = "http://boss-api-test.gbboss.com" + BOSS_API; // 测试 外网
    String BASE_URL_5 = "http://192.168.0.92" + BOSS_API; // 测试

    /**
     * 广告页
     */
    String AD_URL = "/mobile-api/origin/getIntoAppAd.html";


    /**
     * 彩票host
     */
    String LT_HOST_URL = "/mobile-api/app/getHost.html";

    /**
     * 消息推送域名
     */
    String WEB_SOCKET_URL = "/mdcenter/websocket/msite?localeType=zh_CN";

    /**
     * 动态挂维护
     */
    String MAINTAIN_URL = "/__error_/608info.html";

    /**
     * 更新 app
     */
    String UPDATE_URL = "/mobile-api/app/update.html";
    /**
     * 登录URL
     */
    String LOGIN_URL = "/passport/login.html";


    String GET_SID = "/mobile-api/origin/getHttpCookie.html";
    /**
     * 真实姓名验证
     */
    String REAL_NAME_URL = "/mobile-api/userInfoOrigin/verifyRealNameForApp.html";
    /**
     * 验证码URL
     */
    String CAPTCHA_URL = "/captcha/code.html";

    /**
     * 错误线路收集接口
     */
    String COLLECT_APP_DOMAINS = "/facade/collectAppDomainError.html";
    /**
     * 客服
     */
//    String SERVICE_URL = "/index/getCustomerService.html";
    String SERVICE_URL = "/mobile-api/origin/getCustomerService.html";
    /**
     * 存款
     */
    String DEPOSIT_URL = "/wallet/deposit/index.html";
    /**
     * 注册验证码
     */
    String REGISTER_CAPTURE_CODE = "/captcha/pmregister.html";

    /**
     * 注册接口
     */
    String GET_REGISTER_INFO = "/mobile-api/registerOrigin/getRegisterInfo.html";

    /**
     * 注册保存
     */
    String POST_REGISTER_SAVE = "/mobile-api/registerOrigin/save.html";

    /**
     * 43.找回密码判断用户是否绑定过手机号接口:
     */
    String FIND_PASSWOR_IS_BIND_PHONE = "/mobile-api/findPasswordOrigin/findUserPhone.html";

    /**
     * 44.找回密码发送手机短信接口:
     */
    String SEND_MESSAGE_COED = "/mobile-api/origin/sendFindPasswordPhone.html";

    /**
     * 45.找回密码验证手机短信验证码接口:
     */
    String VIRIFICATION_CODE = "/mobile-api/findPasswordOrigin/checkPhoneCode.html";

    /**
     * 46.找回登录密码接口:
     */
    String SET_NEW_PASSWPRD = "/mobile-api/findPasswordOrigin/findLoginPassword.html";


    /**
     * webView 类型
     */
    String WEBVIEW_URL = "WEBVIEW_URL";
    String WEBVIEW_TYPE = "WEBVIEW_TYPE";
    String WEBVIEW_TYPE_GAME = "WEBVIEW_TYPE_GAME";
    String WEBVIEW_TYPE_GAME_FULLSCREEN_ALWAYS = "WEBVIEW_TYPE_GAME_FULLSCREEN_ALWAYS";
    // 平台一般网页
    String WEBVIEW_TYPE_ORDINARY = "WEBVIEW_TYPE_ORDINARY";
    // 第三方一般网页
    String WEBVIEW_TYPE_THIRD_ORDINARY = "WEBVIEW_TYPE_THIRD_ORDINARY";

    /**
     * 一般参数
     */
    String KEY_USERNAME = "KEY_USERNAME";
    String KEY_PASSWORD = "KEY_PASSWORD";
    String KEY_USERNAME_AUTO_LOGIN = "KEY_USERNAME_AUTO_LOGIN";
    String KEY_PASSWORD_AUTO_LOGIN = "KEY_PASSWORD_AUTO_LOGIN";
    String KEY_TIME_AUTO_LOGIN = "KEY_TIME_AUTO_LOGIN";
    String KEY_NEED_CAPTCHA = "KEY_NEED_CAPTCHA";
    String KEY_CAPTCHA_TIME = "KEY_CAPTCHA_TIME";
    String KEY_CUSTOMER_SERVICE = "KEY_CUSTOMER_SERVICE";
    String KEY_CUSTOMER_SERVICE_ISINLAY = "KEY_CUSTOMER_SERVICE_ISINLAY";
    String KEY_REMEMBER_PWD = "KEY_REMEMBER_PWD";
    int KEY_REGIST_BACK_LOGIN = 23333;

    int KEY_FIND_PASSWORD = 333;

    /**
     * 登录后
     */
    String EVENT_TYPE_LOGINED = "EVENT_TYPE_LOGINED";
    /**
     * 跳转到存款
     */
    String EVENT_TYPE_GOTOTAB_DEPOSIT = "EVENT_TYPE_GOTOTAB_DEPOSIT";
    /**
     * 跳转到资金转换
     */
    String EVENT_TYPE_QUOTA_CONVERSION = "EVENT_TYPE_QUOTA_CONVERSION";
    /**
     * 跳转到取款
     */
    String EVENT_TYPE_WITHDRAW_MONEY = "EVENT_TYPE_WITHDRAW_MONEY";
    /**
     * 跳转到主页
     */
    String EVENT_TYPE_GOTOTAB_HOME = "EVENT_TYPE_GOTOTAB_HOME";
    /**
     * 跳转到客服
     */
    String EVENT_TYPE_GOTOTAB_SERVICE = "EVENT_TYPE_GOTOTAB_SERVICE";
    /**
     * 跳转到我的
     */
    String EVENT_TYPE_GOTOTAB_MINE = "EVENT_TYPE_GOTOTAB_MINE";

    /**
     * 回收个别
     */
    String EVENT_BACK_MONEY = "EVENT_BACK_MONEY";


    /**
     * 一键回收
     */
    String EVENT_TYPE_ONE_KEY_BACK = "EVENT_TYPE_ONE_KEY_BACK";

    /**
     * 我的链接和用户数据
     */
    String EVENT_TYPE_MINE_LINK = "EVENT_TYPE_MINE_LINK";
    /**
     * 申请优惠  选中申请优惠类型
     */
    String EVENT_TYPE_APP_PREF = "EVENT_TYPE_APP_PREF";
    /**
     * 登出帐户
     */
    String EVENT_TYPE_LOGOUT = "EVENT_TYPE_LOGOUT";
    /**
     * 賬戶更新
     */
    String EVENT_TYPE_ACCOUNT = "EVENT_TYPE_ACCOUNT";
    /**
     * 接口失败，抛出异常
     * 页面有刷新或者加载更多，或者页面要在抛出异常处理一些事物
     */
    String EVENT_TYPE_NETWORK_EXCEPTION = "EVENT_TYPE_NETWORK_EXCEPTION";
    /**
     * 刷新我的界面用户数据
     */
    String EVENT_TYPE_REFRESH_MINE_DATA = "EVENT_TYPE_REFRESH_MINE_DATA";
    /**
     * 注册成功
     */
    String EVEN_TYPE_REGISTER_SUCCEED = "even_type_register_succeed";
    /**
     * 通知点击客服
     */
    String EVEN_TYPE_SERVICE_CLICK = "EVEN_TYPE_SERVICE_CLICK";

    /**
     * 连接到webSocket长连接
     */
    String EVENT_CONNECT_WEB_SOCKET = "event_connect_web_socket";

    /**
     * 切断webSocket长连接
     */
    String EVENT_DISCONNECT_WEB_SOCKET = "event_disconnect_web_socket";

    /**
     * 是否开启手机找回密码
     */
    String IS_OPEN_PWD_BY_PHONE = "/mobile-api/findPasswordOrigin/openFindByPhone.html";

    /**
     * 是否有引导图
     */
    String IS_GUIDE_IMG = "IS_GUIDE_IMG";

    /**
     * 存款的点击事件
     */
    String DEPOSIT_CLICK_LISTENER = "DEPOSIT_CLICK_LISTENER";

    /**
     * 跳转存款的的碎片
     */
    String DEPOSIT_FRAGMENT_CLICK_LISTENER = "DEPOSIT_FRAGMENT_CLICK_LISTENER";
    /**
     * 取款刷新界面
     */
    String WITHDRAW_MONEY_FRAGMENT_LOAD = "WITHDRAW_MONEY_FRAGMENT_LOAD";
    /**
     * 资金转换刷新界面
     */
    String QUOTA_CONVERSION_FRAGMENT_LOAD = "QUOTA_CONVERSION_FRAGMENT_LOAD";
    /**
     * 接口单页请求数据条数
     */
    int RECORD_LIST_PAGE_SIZE = 12;
    int RECORD_LIST_PAGE_NUMBER = 1;

    /**
     * 分享接口默认20条数据
     */
    int share_RECORD_LIST_PAGE_SIZE = 20;

    /**
     * 系统配置
     */
    String THEME_COLOR_BLUE = "blue";
    String THEME_COLOR_RED = "red";
    String THEME_COLOR_GREEN = "green";
    String THEME_COLOR_BLACK = "black";
    String VOICE_ON_CLICK = "VOICE_ON_CLICK";
    String COPY_RIGHT = "COPYRIGHT © 2004-2018";

    /**
     * 线上支付
     */
    String ONLINE = "online";
    /**
     * 比特币
     */
    String BITCOIN = "bitcoin";
    /**
     * 微信支付
     */
    String WECHAT = "wechat";
    /**
     * 网银存款
     */
    String COMPANY = "company";

    /**
     * 支付宝支付
     */
    String ALIPAY = "alipay";

    /**
     * QQ钱包
     */
    String QQ = "qq";
    /**
     * 京东钱包
     */
    String JD = "jd";
    /**
     * "百度钱包"
     */
    String BD = "bd";
    /**
     * 一码付
     */
    String ONECODEPAY = "onecodepay";
    /**
     * "银联扫码"
     */
    String UNIONPAY = "unionpay";
    /**
     * "柜员机"
     */
    String COUNTER = "counter";

    /**
     * "易收付"
     */
    String EASYPAY = "easy";
    /**
     * "其他"
     */
    String OTHER = "other";
    /**
     * "快充中心"
     */
    String IS_FASTRECHARGE = "isfastrecharge";
    /**
     * 清除缓存
     */
    String EVENT_CLEAR_CACHE = "EVENT_CACHE";

    String packageQQ = "com.tencent.mobileqq";
    String packageali = "com.eg.android.AlipayGphone";
    String packagewechat = "com.tencent.mm";
    String packagejd = "com.wangyin.payment";
    String packageBd = "com.baidu.wallet";

    String PERMISSIONS_STORAGE_WRITE = "android.permission.WRITE_EXTERNAL_STORAGE";
    String PERMISSIONS_STORAGE_READ = "android.permission.READ_EXTERNAL_STORAGE";


    final String KEY_CODE = "key.update";
    final String APP_TYPE = "android";

    String DOWNLOAD_APK_ID = "constants.download_apk_id";
}
