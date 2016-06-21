package com.zyx.controller.pg;

import com.zyx.rpc.pg.PgFacade;
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
 * @author XiaoWei
 * @version V 1.0
 * @package com.controller
 * Create by XiaoWei on 2016/6/14
 */
@RestController
public class PgController {
    @Resource
    private PgFacade pgFacade;

    @RequestMapping(value = "/v1/circle/add", method = RequestMethod.POST)
    public ModelAndView addCircle(String token, String title, Integer createId, Integer circleMasterId, Integer state, Integer type, String details, String headImgUrl) {
        Map<String, Object> map = pgFacade.insertCircle(title, createId, circleMasterId, state, type, details, headImgUrl);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/meetting", method = RequestMethod.GET)
    public ModelAndView addMeet(String token, Integer circleId, Integer accountId) {
        Map<String, Object> map = pgFacade.addMeet(circleId, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/add", method = RequestMethod.POST)
    public ModelAndView addCern(@RequestParam(name = "token",value = "",required = false)String token,
                                @RequestParam(name = "userId")Integer userId,
                                @RequestParam(name = "type")Integer type,
                                @RequestParam(name = "cernTitle")String cernTitle,
                                @RequestParam(name = "content")String content,
                                @RequestParam(name = "imgUrl")String imgUrl,
                                @RequestParam(name = "videoUrl")String videoUrl,
                                @RequestParam(name = "visible")Integer visible) {
        Map<String, Object> map = pgFacade.addCern(userId, type, cernTitle, content, imgUrl, videoUrl, visible);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/zan/add", method = RequestMethod.GET)
    public ModelAndView zan(String token, Integer bodyId, Integer bodyType, Integer accountId) {
        Map<String, Object> map = pgFacade.addZan(bodyId, bodyType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/addMyConcern", method = RequestMethod.GET)
    public ModelAndView myConcern(String token, Integer concernId, Integer concernType, Integer accountId) {
        Map<String, Object> map = pgFacade.addMyConcern(concernId, concernType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/list",method = RequestMethod.GET)
    public ModelAndView circleList(Integer max){
        Map<String, Object> map = pgFacade.circleList(max);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/random",method = RequestMethod.GET)
    public ModelAndView random(Integer n){
        Map<String, Object> map = pgFacade.starRandom(1,n);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/starRandom",method = RequestMethod.GET)
    public ModelAndView starRandom(Integer n){
        Map<String, Object> map = pgFacade.starRandom(3,n);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

}
