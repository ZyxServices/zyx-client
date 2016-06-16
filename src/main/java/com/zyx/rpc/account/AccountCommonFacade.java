package com.zyx.rpc.account;

import java.util.Map;

/**
 * Created by WeiMinSheng on 2016/6/15.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title AccountCommonFacade.java
 */
public interface AccountCommonFacade {
    /**
     * @param phone
     * @param msg
     * @return
     */
    Map<String, Object> sendPhoneCode(String phone, String msg);
}
