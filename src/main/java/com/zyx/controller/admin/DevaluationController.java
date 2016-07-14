package com.zyx.controller.admin;

import java.util.HashMap;
import java.util.Map;

import com.zyx.rpc.pg.PgFacade;
import com.zyx.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.zyx.constants.Constants;
import com.zyx.constants.live.LiveConstants;
import com.zyx.rpc.account.UserDevaFacade;
import com.zyx.rpc.activity.ActivityDevaFacade;
import com.zyx.rpc.live.LiveInfoFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/deva")
@Api(description = "首推相关接口")
public class DevaluationController {

    @Autowired
    LiveInfoFacade liveInfoFacade;
    @Autowired
    private ActivityDevaFacade activityDevaFacade;
    @Autowired
    private UserDevaFacade userDevaFacade;
    @Autowired
    private PgFacade pgFacade;


    @RequestMapping(value = "/refreshAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "首推-刷新 所有首推", notes = "-刷新 所有首推")
    public ModelAndView refershAll() {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);

        // 直播首推
        liveInfoFacade.refreshDevaLives();
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/refreshModel", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "首推-刷新 所有单个首推", notes = "-刷新 所有单个首推")
    public ModelAndView refershModel(@RequestParam(name = "model") Integer model) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
        attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_SUCCESS);
        if (model == null || !(model != 1 || model != 2 || model != 3 || model != 4 || model != 5)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            switch (model) {
                case 1:

                case 2:// 直播首推
                    liveInfoFacade.refreshDevaLives();
                    break;
                default:
                    attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
                    attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
                    attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
                    break;
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "首推-按照模块获取所有首推", notes = "首推-按照模块获取所有首推")
    public ModelAndView getDeva(@RequestParam(name = "model") Integer model) {
        Map<String, Object> attrMap = new HashMap<>();
        if (model == null || !(model < 1 && model > 5)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            Map<String, Object> devasMap = new HashMap<>();
            switch (model) {
                case 1:
                    devasMap.put("activtyDevas", activityDevaFacade.queryActivityDeva());
                    break;
                case 2:// 直播首推
                    devasMap.put("liveDevas", liveInfoFacade.getDevaLives());
                    devasMap.put("liveWatchNums", liveInfoFacade.getLiveDevaWatchNum());
                    break;
                case 3:
                    devasMap.put("cirleDevas", pgFacade.queryCircleDeva());
                    break;
                case 4:
                    devasMap.put("concerDevas", pgFacade.queryConcernDeva());
                    break;
                case 5:
                    devasMap.put("userDevas", userDevaFacade.queryUserDeva());
                    break;
                default:
                    break;
            }
            attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            attrMap.put(LiveConstants.SUCCESS_MSG, LiveConstants.MSG_SUCCESS);
            attrMap.putAll(devasMap);
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "首推-按照模块获取所有首推", notes = "首推-按照模块获取所有首推")
    public ModelAndView getAllDeva() {
        Map<String, Object> attrMap;
        try {
            Map<String, Object> devasMap = new HashMap<>();
            devasMap.put("activtyDevas", getActivityDeva());
            devasMap.put("liveDevas", getDevaLives());
            devasMap.put("liveWatchNums", getLiveDevaWatchNum());
            devasMap.put("userDevas", queryUserDeva());
            devasMap.put("cirleDevas", queryCircleDeva());
            devasMap.put("concerDevas", queryConcernDeva());
            attrMap = MapUtils.buildSuccessMap(LiveConstants.SUCCESS, LiveConstants.MSG_SUCCESS, devasMap);
        } catch (Exception e) {
            attrMap = Constants.MAP_500;
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    private Object getActivityDeva() {
        try {
            return activityDevaFacade.queryActivityDeva();
        } catch (Exception e) {
            return "";
        }
    }

    private Object getDevaLives() {
        try {
            return liveInfoFacade.getDevaLives();
        } catch (Exception e) {
            return "";
        }
    }

    private Object getLiveDevaWatchNum() {
        try {
            return liveInfoFacade.getLiveDevaWatchNum();
        } catch (Exception e) {
            return "";
        }
    }

    private Object queryUserDeva() {
        try {
            return userDevaFacade.queryUserDeva();
        } catch (Exception e) {
            return "";
        }
    }

    private Object queryCircleDeva() {
        try {
            return pgFacade.queryCircleDeva();
        } catch (Exception e) {
            return "";
        }
    }

    private Object queryConcernDeva() {
        try {
            return pgFacade.queryConcernDeva();
        } catch (Exception e) {
            return "";
        }
    }

}
