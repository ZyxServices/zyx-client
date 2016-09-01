package com.zyx.controller.attention;

import com.zyx.constants.Constants;
import com.zyx.param.attention.AttentionParam;
import com.zyx.rpc.attention.UserAttentionFacade;
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
 * Created by wms on 2016/8/16.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title UserAttentionController.java
 */
@RestController
@RequestMapping("/v1/attention")
@Api(description = "用户关注相关接口。1、用户A关注用户B。")
public class UserAttentionController {

    @Autowired
    private UserAttentionFacade userAttentionFacade;

    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    @ApiOperation(value = "用户A关注用户B", notes = "用户A关注用户B")
    public ModelAndView attention_get(@RequestParam(name = "token") String token, @RequestParam(name = "fromId") Integer fromId, @RequestParam(name = "toId") Integer toId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(fromId) || StringUtils.isEmpty(toId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            AttentionParam param = buildParam(token, fromId, toId);
            jsonView.setAttributesMap(userAttentionFacade.attentionFromAToB(param));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/user", method = {RequestMethod.DELETE})
    @ApiOperation(value = "用户A取消关注用户B", notes = "用户A取消关注用户B")
    public ModelAndView attention_delete(@RequestParam(name = "token") String token, @RequestParam(name = "fromId") Integer fromId, @RequestParam(name = "toId") Integer toId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(fromId) || StringUtils.isEmpty(toId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            AttentionParam param = buildParam(token, fromId, toId);
            jsonView.setAttributesMap(userAttentionFacade.unAttentionFromAToB(param));
        }
        return new ModelAndView(jsonView);
    }

    private AttentionParam buildParam(String token, Integer fromId, Integer toId) {
        AttentionParam param = new AttentionParam(fromId, toId);
        param.setToken(token);
        return param;
    }

}
