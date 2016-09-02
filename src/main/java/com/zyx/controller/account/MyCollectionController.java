package com.zyx.controller.account;

import com.zyx.rpc.account.MyCollectionFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wms on 2016/8/24.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v1/my/collection")
@Api(description = "我的收藏API。1、查询我的收藏列表。")
public class MyCollectionController {
    @Resource
    private MyCollectionFacade myCollectionFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "我的收藏列表", notes = "我的收藏列表")
    public ModelAndView myCircleList(@RequestParam String token, @RequestParam Integer accountId) {
        Map<String, Object> map = myCollectionFacade.myList(token, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}