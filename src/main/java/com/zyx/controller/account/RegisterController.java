package com.zyx.controller.account;

import com.utils.FileUploadUtils;
import com.utils.ImagesVerifyUtils;
import com.zyx.constants.Constants;
import com.zyx.entity.account.UserLoginParam;
import com.zyx.rpc.account.RegisterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

    @RequestMapping(value = "/validate/code", method = RequestMethod.POST)
    public ModelAndView validatePhoneCode(@RequestParam(name = "phone") String phone, @RequestParam(name = "code") String code) {

        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setPhone(phone);
            userLoginParam.setCode(code);
            jsonView.setAttributesMap(registerFacade.validatePhoneCode(userLoginParam));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam(name = "phone") String phone,
                                 @RequestParam(name = "pwd") String password,
//                                 @RequestParam(name = "code") String code,
                                 @RequestParam(name = "nickname") String nickname,
                                 @RequestParam(name = "avatar", required = false) MultipartFile avatar) {

        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            jsonView.setAttributesMap(buildMissParamMap());
        } else {
            if (avatar != null) {// 用户上传头像
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

    private Map<String, Object> buildMissParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.STATE, Constants.PARAM_MISS);
        map.put(Constants.ERROR_MSG, Constants.MSG_PARAM_MISS);
        return map;
    }
}