package com.constants;

/**
 * Created by WeiMinSheng on 2016/6/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title AuthConstants.java
 */
public interface AuthConstants {
    /**
     * 返回code信息
     */
    Integer AUTH_ERROR_100 = 100;

    Integer AUTH_SUCCESS_200 = 200;

    // 信息发送失败
    Integer AUTH_ERROR_10005 = 10005;

    //参数缺失
    Integer AUTH_ERROR_10016 = 10016;

    // 登陆
    int ACCOUNT_LOGIN_SUCCESS = 10000;

    int ACCOUNT_LOGIN_ERROR = 10002;

    int ACCOUNT_INFO_ERROR = 10001;

    String AUTH_STATE = "state";
    String AUTH_SUCCESS = "success";

    String AUTH_ERRCODE = "errcode";
    String AUTH_ERRMSG = "errmsg";

    // =================MSG==========
    /**
     * 参数错误，请求失败
     */
    String MISS_PARAM_ERROR = "参数错误，请求失败";

    String TIMESTAMP_A = "timestamp_a";

    String TIMESTAMP_B = "timestamp_b";
}
