package com.constants;

/**
 * Created by WeiMinSheng on 2016/6/12.
 *
 * 1.代码分配：
 * 
 * 10000以下为系统通用代码 10000~19999为： 20000~29999为：直播模块特用代码 30000~39999为：
 * 40000~49999为：
 * 
 * 2.消息字符串分配 系统消息格式：MSG_消息名 各模块消息格式：MSG_模块名_消息
 * 
 * 直播模块：MSG_LIVE_
 * 
 * @author WeiMinSheng
 * @version V1.0 Copyright (c)2016 tyj-版权所有
 * @title AuthConstants.java
 */
public interface AuthConstants {

	////////////// 通用标识符 开始//////////////////
	/**
	 * 状态
	 */
	String STATE = "state";
	/**
	 * 错误码
	 */
	String ERRCODE = "errcode";
	/**
	 * 错误信息
	 */
	String ERRMSG = "errmsg";
	
	String SUCCESS_MSG = "successmsg";
	////////////// 通用标识符开始//////////////////

	////////////// 通用消息 开始//////////////////
	/**
	 * 成功
	 */
	String MSG_SUCCESS = "success";

	////////////// 通用消息 开始//////////////////
	/**
	 * 返回code信息
	 */
	////////////// 系统代码 开始//////////////////
	////////
	/**
	 * 请求错误总代码
	 */
	int ERROR = 100;

	/**
	 * 请求成功代码
	 */
	int SUCCESS = 200;

	/// 参数错误类型代码 300~399
	/**
	 * 参数错误总代码
	 */
	int PARAM_ERROR = 300;
	/**
	 * 参数缺失
	 */
	int PARAM_MISS = 301;

	/**
	 * 参数非法 如类型 长度 大小值 错误
	 */
	int PARAM_ILIGAL = 302;

	/// 请求错误代码
	/**
	 * 请求错误代码
	 */
	int REQUEST_ERROR = 400;
	/**
	 * 无权限请求
	 */
	int REQUEST_UNAUTHORIZED = 401;

	////////
	////////////// 系统代码 结束//////////////////
	// 信息发送失败
	int AUTH_ERROR_10005 = 10005;

	// 参数缺失
	int AUTH_ERROR_10016 = 10016;

	// 登陆
	int ACCOUNT_LOGIN_SUCCESS = 10000;

	int ACCOUNT_LOGIN_ERROR = 10002;

	int ACCOUNT_INFO_ERROR = 10001;

	// =================MSG==========
	/**
	 * 参数错误，请求失败
	 */
	String MISS_PARAM_ERROR = "参数错误，请求失败";

	String TIMESTAMP_A = "timestamp_a";

	String TIMESTAMP_B = "timestamp_b";
}
