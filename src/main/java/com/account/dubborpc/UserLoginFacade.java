package com.account.dubborpc;

import java.util.Map;

/**
 * Created by WeiMinSheng on 2016/6/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title UserLoginFacade.java
 */
public interface UserLoginFacade {
    Map<String, Object> loginByPhoneAndPassword(String phone, String password);

    Map<String, Object> signout(String token);
}
