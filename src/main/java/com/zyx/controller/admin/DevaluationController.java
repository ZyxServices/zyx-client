package com.zyx.controller.admin;

import com.zyx.config.BaseResponse;
import com.zyx.config.responses.DevaResponse;
import com.zyx.constants.Constants;
import com.zyx.constants.live.LiveConstants;
import com.zyx.rpc.system.DevaFacade;
import com.zyx.utils.MapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/deva")
@Api(description = "首推相关接口")
public class DevaluationController {
    @Autowired
    private DevaFacade devaFacade;
//    /*@ApiParam(required = true,name = "model",value ="所在模块")*/
    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "首推-按照模块获取所有首推", notes = "首推-按照模块获取所有首推",response = BaseResponse.class)
    public ModelAndView getDeva(@ApiParam(required = true,name = "model",value ="首推所在模块")@RequestParam(name = "model",required = true) Integer model) {
        Map<String, Object> attrMap = new HashMap<>();
        if (model == null || Constants.devaNames.get(model)==null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            Map<String, Object> devasMap = new HashMap<>();
            attrMap.put(Constants.devaNames.get(model),devaFacade.getDevaByModel(model));
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
            devasMap.put("activtyDevas", devaFacade.getDevaByModel(Constants.MODEL_ACTIVITY));
            devasMap.put("liveDevas", devaFacade.getDevaByModel(Constants.MODEL_LIVE));
//            devasMap.put("liveWatchNums", getLiveDevaWatchNum());
            devasMap.put("userDevas", devaFacade.getDevaByModel(Constants.MODEL_USER));
            devasMap.put("cirleDevas", devaFacade.getDevaByModel(Constants.MODEL_CIRCLE));
            devasMap.put("concerDevas", devaFacade.getDevaByModel(Constants.MODEL_CONCERN));
            attrMap = MapUtils.buildSuccessMap(LiveConstants.SUCCESS, LiveConstants.MSG_SUCCESS, devasMap);
        } catch (Exception e) {
            attrMap = Constants.MAP_500;
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
