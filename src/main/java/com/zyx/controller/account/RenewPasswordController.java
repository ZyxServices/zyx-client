package com.zyx.controller.account;

import com.zyx.constants.account.AccountConstants;
import com.zyx.entity.account.UserLoginParam;
import com.zyx.rpc.account.RegisterFacade;
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
 * Created by WeiMinSheng on 2016/6/13.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title RenewPasswordController.java
 */
@RestController
@RequestMapping("/v1/account")
@Api(description = "用户密码修改API。1、同步服务器时间戳。2、发送验证码")
public class RenewPasswordController {

    @Autowired
    private RegisterFacade registerFacade;

    @RequestMapping(value = "/renewpwd", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ModelAndView renewpwd(@RequestParam(name = "token") String token, @RequestParam(name = "old_pwd") String password, @RequestParam(name = "new_pwd") String password2) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(password) || StringUtils.isEmpty(password2)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setToken(token);
            userLoginParam.setPassword(password);// 旧密码
            userLoginParam.setPassword2(password2);// 新密码
            jsonView.setAttributesMap(registerFacade.renewpwd(userLoginParam));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/retrievepwd", method = RequestMethod.POST)
    @ApiOperation(value = "忘记密码", notes = "忘记密码，通过手机号和验证码修改密码")
    public ModelAndView retrievepwd(@RequestParam(name = "phone") String phone, @RequestParam(name = "pwd") String password, @RequestParam(name = "code") String code) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setCode(code);
            userLoginParam.setPhone(phone);
            userLoginParam.setPassword2(password);
            jsonView.setAttributesMap(registerFacade.retrievepwd(userLoginParam));
        }

        return new ModelAndView(jsonView);
    }

}
