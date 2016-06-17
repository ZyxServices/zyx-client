package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.constants.account.AccountConstants;
import com.zyx.entity.account.param.UserMarkParam;
import com.zyx.rpc.account.MarkFacade;
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
 * Created by WeiMinSheng on 2016/6/16.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title MarkController.java
 */
@RestController
@RequestMapping("/v1/account")
public class MarkController {

    @Autowired
    private MarkFacade markFacade;

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public ModelAndView sign(@RequestParam(name = "token") String token, @RequestParam(name = "accountId") int userId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            try {
                UserMarkParam userMarkParam = new UserMarkParam();
                userMarkParam.setToken(token);
                userMarkParam.setUserId(userId);
                jsonView.setAttributesMap(markFacade.sign(userMarkParam));
            } catch (Exception e) {
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/querySign", method = RequestMethod.GET)
    public ModelAndView querySign(@RequestParam(name = "token") String token, @RequestParam(name = "accountId") int userId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            try {
                UserMarkParam userMarkParam = new UserMarkParam();
                userMarkParam.setToken(token);
                userMarkParam.setUserId(userId);
                jsonView.setAttributesMap(markFacade.querySign(userMarkParam));
            } catch (Exception e) {
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }


    private Map<String, Object> buildMissParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.STATE, Constants.PARAM_MISS);
        map.put(Constants.ERROR_MSG, Constants.MSG_PARAM_MISS);
        return map;
    }
}