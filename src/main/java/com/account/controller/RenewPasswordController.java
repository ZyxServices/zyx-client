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
    public ModelAndView renewpwd(@RequestParam(name = "token") String token, @RequestParam(name = "old_pwd") String password, @RequestParam(name = "new_pwd") String password2) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(password) || StringUtils.isEmpty(password2)) {
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setToken(token);
            userLoginParam.setPassword(password);// 旧密码
            userLoginParam.setPassword2(password2);// 新密码
            jsonView.setAttributesMap(registerFacade.renewpwd(userLoginParam));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping("/retrievepwd")
    public ModelAndView retrievepwd(@RequestParam(name = "phone") String phone, @RequestParam(name = "pwd") String password, @RequestParam(name = "code") String code) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setCode(code);
            userLoginParam.setPhone(phone);
            userLoginParam.setPassword2(password);
            jsonView.setAttributesMap(registerFacade.retrievepwd(userLoginParam));
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
