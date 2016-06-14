package com.account.controller;

import com.account.dubborpc.RegisterFacade;
import com.account.dubborpc.UserLoginFacade;
import com.constants.AuthConstants;
import com.zyx.entity.account.UserLoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WeiMinSheng on 2016/6/13.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title RenewPasswordController.java
 */
@RestController
@RequestMapping("/v1/account")
public class RenewPasswordController {

    @Autowired
    private RegisterFacade registerFacade;

    @RequestMapping("/renewpwd")
    public ModelAndView renewpwd(@RequestParam(name = "token", required = true) String token,
                                 @RequestParam(name = "old_pwd", required = true) String password,
                                 @RequestParam(name = "new_pwd", required = true) String password2) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(password) || StringUtils.isEmpty(password2)) {
            map.put(AuthConstants.AUTH_ERRCODE, AuthConstants.AUTH_ERROR_10016);
            map.put(AuthConstants.AUTH_ERRMSG, AuthConstants.MISS_PARAM_ERROR);
            jsonView.setAttributesMap(map);
        } else {
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setToken(token);
            userLoginParam.setPassword(password);// 旧密码
            userLoginParam.setPassword2(password2);// 新密码
            map = registerFacade.renewpwd(userLoginParam);
            jsonView.setAttributesMap(map);
        }

        return new ModelAndView(jsonView);
    }
}
