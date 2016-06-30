package com.zyx.controller.activity;

import com.zyx.entity.Devaluation;
import com.zyx.rpc.activity.ActivityDevaFacade;
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
 * Created by SubDong on 16-6-28.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title ActivityDevaController
 * @package com.zyx.controller.activity
 * @update 16-6-28 下午3:39
 */
@RestController
@RequestMapping("/v1/devaluation")
public class ActivityDevaController {

    @Resource
    private ActivityDevaFacade activityDevaFacade;

    @RequestMapping(value = "/activityDeva", method = RequestMethod.POST)
    @ApiOperation(value = "活动接口", notes = "首页首推")
    public ModelAndView activityDeva() {
        AbstractView jsonView = new MappingJackson2JsonView();
//        Map<String, Object> map = activityDevaFacade.queryActivityDeva();
//        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/insterDevaluation", method = RequestMethod.POST)
    @ApiOperation(value = "首推接口", notes = "首页首推")
    public ModelAndView inster(@RequestParam(name = "types", required = true) Integer types,
                               @RequestParam(name = "devaluationId", required = true) Integer devaluationId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Devaluation devaluation = new Devaluation();
        devaluation.setTypes(types);
        devaluation.setDevaluationId(devaluationId);
        Map<String, Object> map = activityDevaFacade.insterActivityDeva(devaluation);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/delDevaluation", method = RequestMethod.POST)
    @ApiOperation(value = "首推接口", notes = "取消首页首推")
    public ModelAndView delDevaluation(@RequestParam(name = "types", required = true) Integer types,
                                       @RequestParam(name = "devaluationId", required = true) Integer devaluationId) {


        AbstractView jsonView = new MappingJackson2JsonView();
        Devaluation devaluation = new Devaluation();
        devaluation.setTypes(types);
        devaluation.setDevaluationId(devaluationId);
        Map<String, Object> map = activityDevaFacade.delActivityDeva(devaluation);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
