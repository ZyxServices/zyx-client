package com.account.controller;

import com.account.dubborpc.UserLoginFacade;
import com.constants.AuthConstants;
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
 * Created by skmbg on 2016/6/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.account.controller
 */
@RestController
@RequestMapping("/v1/account")
public class LoginController {

    @Autowired
    private UserLoginFacade userLoginFacade;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(name = "phone", required = true) String phone, @RequestParam(name = "pwd", required = true) String password) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {// 缺少参数
            map.put(AuthConstants.AUTH_ERRCODE, AuthConstants.AUTH_ERROR_10016);
            map.put(AuthConstants.AUTH_ERRMSG, AuthConstants.MISS_PARAM_ERROR);
            jsonView.setAttributesMap(map);
        } else {
            jsonView.setAttributesMap(userLoginFacade.loginByPhoneAndPassword(phone, password));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping("/signout")
    public ModelAndView signout(@RequestParam(name = "token", required = true) String token) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            map.put(AuthConstants.AUTH_ERRCODE, AuthConstants.AUTH_ERROR_10016);
            map.put(AuthConstants.AUTH_ERRMSG, AuthConstants.MISS_PARAM_ERROR);
            jsonView.setAttributesMap(map);
        } else {// 退出
            jsonView.setAttributesMap(userLoginFacade.signout(token));
        }

        return new ModelAndView(jsonView);
    }
}
