package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.constants.account.AccountConstants;
import com.zyx.rpc.account.AccountCommonFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WeiMinSheng on 2016/6/14.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title CommonController.java
 */
@RestController
@RequestMapping("/v1")
public class CommonController {

    @Autowired
    private AccountCommonFacade accountCommonFacade;

    @RequestMapping(value = "/timestamp", method = RequestMethod.GET)
    public ModelAndView timestamp() {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
        map.put(AccountConstants.TIMESTAMP_LONG, System.currentTimeMillis());
        map.put(AccountConstants.TIMESTAMP_STRING, str);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/sendCode", method = RequestMethod.GET)
    public ModelAndView sendCode(@RequestParam(name = "phone") String phone) {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(phone)) {
            map.put(Constants.STATE, Constants.PARAM_MISS);
            map.put(Constants.ERROR_MSG, Constants.MSG_PARAM_MISS);
            jsonView.setAttributesMap(map);
        } else {
            // 判断手机号码
            if (isMobileNum(phone)) {
                jsonView.setAttributesMap(accountCommonFacade.sendPhoneCode(phone, null));
            } else {
                map.put(Constants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50100);
                map.put(Constants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50100_MSG);
                jsonView.setAttributesMap(map);
            }
        }

        return new ModelAndView(jsonView);
    }

    private boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}