package com.zyx.controller.pg;

import com.zyx.rpc.pg.PgFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
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
    @ApiOperation(value = "添加圈子", notes = "添加圈子")
    public ModelAndView addCircle(@RequestParam("token")String token,
                                  @RequestParam("title")String title,
                                  @RequestParam("createId")Integer createId,
                                  @RequestParam("state")Integer state,
                                  @RequestParam("type")Integer type,
                                  @RequestParam("details")String details,
                                  @RequestParam("headImgUrl")String headImgUrl) {
        Map<String, Object> map = pgFacade.insertCircle(title, createId, state, type, details, headImgUrl);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/meetting", method = RequestMethod.POST)
    @ApiOperation(value = "圈子签到", notes = "圈子签到")
    public ModelAndView addMeet(@RequestParam("token") String token,
                                @RequestParam("circleId") Integer circleId,
                                @RequestParam("accountId") Integer accountId) {
        Map<String, Object> map = pgFacade.addMeet(circleId, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/insert", method = RequestMethod.POST)
    @ApiOperation(value = "发布动态", notes = "发布动态")
    public ModelAndView addCern(@RequestParam(name = "token", value = "", required = false) String token,
                                @RequestParam(name = "userId") Integer userId,
                                @RequestParam(name = "type") Integer type,
                                @RequestParam(name = "cernTitle") String cernTitle,
                                @RequestParam(name = "content") String content,
                                @RequestParam(name = "imgUrl") String imgUrl,
                                @RequestParam(name = "videoUrl") String videoUrl,
                                @RequestParam(name = "visible") Integer visible) {
        Map<String, Object> map = pgFacade.addCern(userId, type, cernTitle, content, imgUrl, videoUrl, visible);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/zan/add", method = RequestMethod.POST)
    @ApiOperation(value = "点赞", notes = "点赞")
    public ModelAndView zan(@RequestParam(name = "token")String token,
                            @RequestParam(name = "bodyId")Integer bodyId,
                            @RequestParam(name = "bodyType")Integer bodyType,
                            @RequestParam(name = "accountId")Integer accountId) {
        Map<String, Object> map = pgFacade.addZan(bodyId, bodyType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加关注", notes = "添加关注")
    public ModelAndView addMyConcern(@RequestParam(name = "token")String token,
                                     @RequestParam(name = "concernId")Integer concernId,
                                     @RequestParam(name = "concernType")Integer concernType,
                                     @RequestParam(name = "accountId")Integer accountId) {
        Map<String, Object> map = pgFacade.addMyConcern(concernId, concernType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/list/{token}/{max}", method = RequestMethod.GET)
    @ApiOperation(value = "圈子列表", notes = "圈子列表")
    public ModelAndView circleList(@PathVariable String token,@PathVariable Integer max) {
        Map<String, Object> map = pgFacade.circleList(max);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/random/{token}/{n}", method = RequestMethod.GET)
    @ApiOperation(value = "个人圈子列表", notes = "个人圈子列表")
    public ModelAndView random(@PathVariable String token,@PathVariable  Integer n) {
        Map<String, Object> map = pgFacade.starRandom(1, n);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/starRandom/{token}/{n}", method = RequestMethod.GET)
    @ApiOperation(value = "明星圈子列表", notes = "明星圈子列表")
    public ModelAndView starRandom(@PathVariable String token,@PathVariable Integer n) {
        Map<String, Object> map = pgFacade.starRandom(3, n);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circleItem/add", method = RequestMethod.POST)
    @ApiOperation(value = "发布帖子", notes = "发布帖子")
    public ModelAndView addCircleItem(@RequestParam(name = "token")String token,
                                      @RequestParam(name = "circle_id")Integer circle_id,
                                      @RequestParam(name = "create_id")Integer create_id,
                                      @RequestParam(name = "title")String title,
                                      @RequestParam(name = "content")String content) {
        Map<String, Object> map = pgFacade.addCircleItem(circle_id, create_id, title, content);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/setMaster", method = RequestMethod.POST)
    @ApiOperation(value = "设置圈主", notes = "设置圈主")
    public ModelAndView setMaster(@RequestParam(name = "token")String token,
                                  @RequestParam(name = "circle_id")Integer circle_id,
                                  @RequestParam(name = "master_id")Integer master_id,
                                  @RequestParam(name = "account_id")Integer account_id) {
        Map<String, Object> map = pgFacade.setMaster(circle_id, master_id, account_id);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/delete/{token}/{circle_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除圈子", notes = "删除圈子")
    public ModelAndView deleteCircle(@PathVariable String token, @PathVariable Integer circle_id) {
        Map<String, Object> map = pgFacade.delete(circle_id);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

}
