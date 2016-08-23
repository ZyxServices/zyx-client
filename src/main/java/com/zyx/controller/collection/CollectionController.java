package com.zyx.controller.collection;

import com.zyx.constants.account.AccountConstants;
import com.zyx.constants.live.LiveConstants;
import com.zyx.entity.collection.Collection;
import com.zyx.entity.live.LiveInfo;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.collection.CollectionFacade;
import com.zyx.vo.account.AccountInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

/**
 * Created by MrDeng on 2016/8/23.
 */

@RestController
@RequestMapping("/v1/collect")
@Api(description = "直播相关接口")
public class CollectionController {

    @Autowired
    AccountCommonFacade accountCommonFacade;
    @Autowired
    CollectionFacade collectionFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加收藏", notes = "收藏-添加收藏记录")
    public ModelAndView createCollection(@RequestParam(name = "token", required = true) String token,
                                         @RequestParam(name = "model", required = true) Integer model,
                                         @RequestParam(name = "modelId", required = true) Integer modelId) {
        // Token 验证
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_REQUEST_UNAUTHORIZED);
        } else if (model == null || modelId == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (!(model == 1 || model == 2 || model == 3 || model == 4 || model == 6 || model == 7)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
            } else {
                Collection collection = new Collection();
                collection.setUsrId(account.getId());
                collection.setModel(model);
                collection.setModelId(modelId);
                collectionFacade.addCollection(collection);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}