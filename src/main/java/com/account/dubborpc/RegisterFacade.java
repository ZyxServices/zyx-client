package com.account.dubborpc;


import com.zyx.entity.account.UserLoginParam;

import java.util.Map;

/**
 * Created by skmbg on 2016/6/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title RegisterFacade.java
 */
public interface RegisterFacade {

    int sendPhoneCode(String phone, String msg);

    Map<String, Object> registerAccount(UserLoginParam userLoginParam);

    Map<String, Object> renewpwd(UserLoginParam userLoginParam);
}
