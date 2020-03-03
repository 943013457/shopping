package com.Util;

/**
 * @Creator Ming
 * @Time 2020/3/2
 * @Other 错误状态码
 */
public class StateCode {
    public static String REQUEST_SUCCESS = "ok";
    public static String REQUEST_ERROR = "error";

    public static int ERR_NOT_LOGIN = -100;//用户未登录
    public static int ERR_NOT_USERNAME = -101;//用户名为空
    public static int ERR_NOT_PASSWORD = -102;//密码为空
    public static int ERR_NOT_BANNED = -103;//账号封禁
    public static int ERR_NOT_VALIDATION = -104;//用户名/密码错误
    public static int ERR_NOT_REGISTER = -105;//注册失败

    public static int ERR_NOT_ORDER = -200;//订单生成失败
}
