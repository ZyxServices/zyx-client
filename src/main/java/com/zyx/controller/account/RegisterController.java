package com.zyx.controller.account;

import com.zyx.utils.FileUploadUtils;
import com.zyx.utils.ImagesVerifyUtils;
import com.zyx.constants.account.AccountConstants;
import com.zyx.entity.account.UserLoginParam;
import com.zyx.rpc.account.RegisterFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
@Api(description = "用户注册接口API。1、验证手机验证码。2、注册")
public class RegisterController {

    @Autowired
    private RegisterFacade registerFacade;

    @RequestMapping(value = "/validate/code", method = RequestMethod.POST)
    @ApiOperation(value = "验证手机验证码", notes = "验证手机号和验证码是否匹配")
    public ModelAndView validatePhoneCode(@RequestParam(name = "phone") String phone, @RequestParam(name = "code") String code) {

        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setPhone(phone);
            userLoginParam.setCode(code);
            jsonView.setAttributesMap(registerFacade.validatePhoneCode(userLoginParam));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", notes = "用户注册，头像可不设置")
    public ModelAndView register(@RequestParam(name = "phone") String phone,
                                 @RequestParam(name = "pwd") String password,
//                                 @RequestParam(name = "code") String code,
                                 @RequestParam(name = "nickname") String nickname,
                                 @RequestPart(name = "avatar", required = false) MultipartFile avatar) {

        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            if (avatar != null) {// 用户上传头像
                System.out.println("avatar  :  " + avatar);
                System.out.println("avatar.getName()  :  " + avatar.getName());
                String avatarId = FileUploadUtils.uploadFile(avatar);
                Map<String, Object> map = ImagesVerifyUtils.verify(avatarId);
                if (map != null) {
                    jsonView.setAttributesMap(map);
                } else {
                    UserLoginParam userLoginParam = new UserLoginParam();
                    userLoginParam.setPhone(phone);
                    userLoginParam.setPassword(password);
//                    userLoginParam.setCode(code);
                    userLoginParam.setAvatar(avatarId);
                    userLoginParam.setNickname(nickname);
                    jsonView.setAttributesMap(registerFacade.registerAccount(userLoginParam));
                }
            } else {
                UserLoginParam userLoginParam = new UserLoginParam();
                userLoginParam.setPhone(phone);
                userLoginParam.setPassword(password);
//                userLoginParam.setCode(code);
                userLoginParam.setNickname(nickname);
                jsonView.setAttributesMap(registerFacade.registerAccount(userLoginParam));
            }
        }
        return new ModelAndView(jsonView);
    }

}