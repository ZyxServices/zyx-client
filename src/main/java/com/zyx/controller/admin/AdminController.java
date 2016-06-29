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

import com.zyx.constants.live.LiveConstants;
import com.zyx.rpc.live.LiveInfoFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/admin")
@Api(description = "直播相关接口")
public class AdminController {

	@Autowired
	LiveInfoFacade liveInfoFacade;
	
	@RequestMapping(value = "/deva/refreshAll", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "运维-刷新 所有首推", notes = "-刷新 所有首推")
	public ModelAndView refershAll() {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
		
		//直播首推
		liveInfoFacade.refreshDevaLives();
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}
	
	@RequestMapping(value = "/deva/refreshModel", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "运维-刷新 所有单个首推", notes = "-刷新 所有单个首推")
	public ModelAndView refershModel(@RequestParam(name = "model")Integer model ){
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
		attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_SUCCESS);
		if(model==null||!(model!=1||model!=2||model!=3||model!=4)){
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
		}else{
			switch (model) {
			case 2://直播首推
				liveInfoFacade.refreshDevaLives();
				break;
			default:
				attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
				attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
				break;
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}
}
