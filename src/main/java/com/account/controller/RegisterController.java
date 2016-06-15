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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WeiMinSheng on 2016/6/6.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title tRegisterController.java
 */
@RestController
@RequestMapping("/v1/account")
public class RegisterController {

    @Autowired
    private RegisterFacade registerFacade;

    @RequestMapping("/sendCode")
    public ModelAndView sendCode(@RequestParam(name = "phone", required = true) String phone) {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(phone)) {
            map.put(AuthConstants.ERRCODE, AuthConstants.AUTH_ERROR_10016);
            map.put(AuthConstants.ERRMSG, "缺少参数");
            jsonView.setAttributesMap(map);
        } else {
            // 判断手机号码
            if (isMobileNum(phone)) {
                int sendMessage = registerFacade.sendPhoneCode(phone, null);
                if (sendMessage == 200) {
                    map.put(AuthConstants.STATE, AuthConstants.SUCCESS);
                    map.put(AuthConstants.MSG_SUCCESS, "短信发送成功！");
                    jsonView.setAttributesMap(map);
                } else if (sendMessage == 501) {
                    map.put(AuthConstants.ERRCODE, AuthConstants.AUTH_ERROR_10005);
                    map.put(AuthConstants.ERRMSG, "短信发送失败！");
                    jsonView.setAttributesMap(map);
                } else if (sendMessage == 502) {
                    map.put(AuthConstants.ERRCODE, AuthConstants.AUTH_ERROR_10005);
                    map.put(AuthConstants.ERRMSG, "短信发送过于频繁，短信发送失败！");
                    jsonView.setAttributesMap(map);
                } else {//-1
                    map.put(AuthConstants.ERRCODE, AuthConstants.ERROR);
                    map.put(AuthConstants.ERRMSG, "未知错误,请重试");
                    jsonView.setAttributesMap(map);
                }
            } else {
                map.put(AuthConstants.ERRCODE, AuthConstants.AUTH_ERROR_10016);
                map.put(AuthConstants.ERRMSG, "手机号输入有误");
                jsonView.setAttributesMap(map);
            }

        }

        return new ModelAndView(jsonView);
    }

    public boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @RequestMapping("/register")
    public ModelAndView register(@RequestParam(name = "phone", required = true) String phone,
                                 @RequestParam(name = "pwd", required = true) String password,
                                 @RequestParam(name = "code", required = true) String code) {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            map.put(AuthConstants.ERRCODE, AuthConstants.AUTH_ERROR_10016);
            map.put(AuthConstants.ERRMSG, AuthConstants.MISS_PARAM_ERROR);
            jsonView.setAttributesMap(map);
        } else {
            //Todo:通过手机号和code码判断是否注册
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setPhone(phone);
            userLoginParam.setPassword(password);
            userLoginParam.setCode(code);
            map = registerFacade.registerAccount(userLoginParam);
            jsonView.setAttributesMap(map);
        }

        return new ModelAndView(jsonView);
    }

}