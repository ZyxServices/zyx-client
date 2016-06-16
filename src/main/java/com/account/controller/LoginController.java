package com.account.controller;

import com.account.dubborpc.UserLoginFacade;
import com.constants.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WeiMinSheng on 2016/6/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title LoginController.java
 */
@RestController
@RequestMapping("/v1/account")
public class LoginController {

    @Autowired
    private UserLoginFacade userLoginFacade;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(name = "phone") String phone, @RequestParam(name = "pwd") String password) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {// 缺少参数
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            jsonView.setAttributesMap(userLoginFacade.loginByPhoneAndPassword(phone, password));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping("/signout")
    public ModelAndView signout(@RequestParam(name = "token") String token) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(buildMissParamMap());
        } else {// 退出
            jsonView.setAttributesMap(userLoginFacade.signout(token));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    public ModelAndView refreshtoken(@RequestParam(name = "token") String token) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(buildMissParamMap());
        } else {// 刷新token并返回新token
            jsonView.setAttributesMap(userLoginFacade.refreshtoken(token));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public ModelAndView sign(@RequestParam(name = "token") String token,
                             @RequestParam(name = "account_id") String accountId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(accountId)) {// 缺少参数
            jsonView.setAttributesMap(buildMissParamMap());
        } else {// 刷新token并返回新token
            jsonView.setAttributesMap(userLoginFacade.sign(token, accountId));
        }

        return new ModelAndView(jsonView);
    }

    private Map<String, Object> buildMissParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(AuthConstants.STATE, AuthConstants.AUTH_ERROR_10016);
        map.put(AuthConstants.ERRMSG, AuthConstants.MISS_PARAM_ERROR);
        return map;
    }

}
