package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.constants.account.AccountConstants;
import com.zyx.entity.Devaluation;
import com.zyx.rpc.account.UserDevaFacade;
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

import java.util.Map;

/**
 * Created by skmbg on 2016/6/29.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.zyx.controller.account
 */
@RestController
@RequestMapping("/v1/devaluation")
@Api(description = "用户首推API。1、查询首推信息。2、插入首推信息。")
public class UserDevaController {
    @Autowired
    private UserDevaFacade userDevaFacade;

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "用户首推查询接口", notes = "用户首推查询接口")
    public ModelAndView userDeva() {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = userDevaFacade.queryUserDeva();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/user/insert", method = RequestMethod.POST)
    @ApiOperation(value = "用户首推插入接口", notes = "用户首推插入接口")
    public ModelAndView insert(@RequestParam(name = "devaluationId") Integer devaluationId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(devaluationId)) {
            jsonView.setAttributesMap(Constants.MAP_500);
        } else {
            // 构建首推纪录
            Devaluation devaluation = new Devaluation();
            devaluation.setTypes(AccountConstants.USER_DEVA_MODEL);
            devaluation.setDevaluationId(devaluationId);
            devaluation.setCreateTime(System.currentTimeMillis());
            // 操作
            jsonView.setAttributesMap(userDevaFacade.insertUserDeva(devaluation));
        }
        return new ModelAndView(jsonView);
    }
}
