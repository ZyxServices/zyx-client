package com.zyx.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.fastjson.JSON;
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

	@RequestMapping(value = "/refreshAll", method = { RequestMethod.GET, RequestMethod.POST })
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

	@RequestMapping(value = "/refreshModel", method = { RequestMethod.GET, RequestMethod.POST })
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

	@RequestMapping(value = "/get", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "首推-按照模块获取所有首推", notes = "首推-按照模块获取所有首推")
	public ModelAndView getDeva(@RequestParam(name = "model") Integer model) {
		Map<String, Object> attrMap = new HashMap<>();
		if (model == null || !(model != 1 || model != 2 || model != 3 || model != 4 || model != 5)) {
			attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
			attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
		} else {
			switch (model) {
			case 1:
				attrMap = activityDevaFacade.queryActivityDeva();
				break;
			case 2:// 直播首推
				attrMap.put("data", liveInfoFacade.getDevaLives());
				break;
			case 5:
				attrMap  = userDevaFacade.queryUserDeva();
				break;
			default:
				break;
			}
			attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			attrMap.put(LiveConstants.SUCCESS_MSG, LiveConstants.MSG_SUCCESS);
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}
}
