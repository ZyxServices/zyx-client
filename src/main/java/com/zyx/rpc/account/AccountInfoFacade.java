package com.zyx.rpc.account;

import java.util.Map;

/**
 * Created by skmbg on 2016/6/17.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.zyx.rpc.account
 */
public interface AccountInfoFacade {
    Map<String, Object> queryAccountInfo(String token, int userId);
}
