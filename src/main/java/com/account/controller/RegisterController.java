package com.account.controller;

import com.account.dubborpc.RegisterFacade;
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
 * Created by WeiMinSheng on 2016/6/6.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title RegisterController.java
 */
@RestController
@RequestMapping("/v1/account")
public class RegisterController {

    @Autowired
    private RegisterFacade registerFacade;

    @RequestMapping("/register")
    public ModelAndView register(@RequestParam(name = "phone") String phone, @RequestParam(name = "pwd") String password, @RequestParam(name = "code") String code) {

        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            //通过手机号和code码判断是否注册
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setPhone(phone);
            userLoginParam.setPassword(password);
            userLoginParam.setCode(code);
            jsonView.setAttributesMap(registerFacade.registerAccount(userLoginParam));
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