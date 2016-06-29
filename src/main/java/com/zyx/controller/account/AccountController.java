package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.rpc.account.AccountInfoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by WeiMinSheng on 2016/6/17.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title AccountController.java
 */
@RestController
@RequestMapping("/v1/account")
@Api(description = "用户信息相关接口。1、查询用户信息。")
public class AccountController {
    @Autowired
    private AccountInfoFacade accountInfoFacade;

    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "通过用户ID查询用户信息", notes = "通过用户ID查询用户信息")
    public ModelAndView login(@RequestParam(name = "token") String token, @RequestParam(name = "account_id") Integer userId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(accountInfoFacade.queryAccountInfo(token, userId));
        }
        return new ModelAndView(jsonView);
    }

}
