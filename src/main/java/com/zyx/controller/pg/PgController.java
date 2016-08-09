package com.zyx.controller.pg;

import com.zyx.constants.Constants;
import com.zyx.controller.common.UploadCommonController;
import com.zyx.entity.pg.Circle;
import com.zyx.entity.pg.Concern;
import com.zyx.rpc.pg.PgFacade;
import com.zyx.utils.FileUploadUtils;
import com.zyx.utils.ImagesVerifyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    public ModelAndView addCircle(@RequestParam("token") String token,
                                  @RequestParam("title") String title,
                                  @RequestParam("createId") Integer createId,
                                  @RequestParam("circleType") Integer circleType,
                                  @RequestParam("details") String details,
                                  @RequestPart(value = "headImgUrl", required = false) MultipartFile headImgUrl) {
        AbstractView jsonView = new MappingJackson2JsonView();

        String imgDbUrl = FileUploadUtils.uploadFile(headImgUrl);
        Map<String, Object> returnResult = ImagesVerifyUtils.verify(imgDbUrl);
        if (returnResult != null) {
            jsonView.setAttributesMap(returnResult);
            return new ModelAndView(jsonView);
        }
        Map<String, Object> map = pgFacade.insertCircle(title, createId, circleType, details, imgDbUrl);
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
                                @RequestPart(name = "imgUrl") MultipartFile imgUrl,
                                @RequestParam(name = "videoUrl") String videoUrl,
                                @RequestParam(name = "visible") Integer visible) {
        AbstractView jsonView = new MappingJackson2JsonView();
        String imgDbUrl = FileUploadUtils.uploadFile(imgUrl);
        Map<String, Object> returnResult = ImagesVerifyUtils.verify(imgDbUrl);
        if (returnResult != null) {
            jsonView.setAttributesMap(returnResult);
            return new ModelAndView(jsonView);
        }
        Map<String, Object> map = pgFacade.addCern(userId, type, cernTitle, content, imgDbUrl, videoUrl, visible);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/zan/add", method = RequestMethod.POST)
    @ApiOperation(value = "点赞", notes = "点赞")
    public ModelAndView zan(@RequestParam(name = "token") String token,
                            @RequestParam(name = "bodyId") Integer bodyId,
                            @RequestParam(name = "bodyType") Integer bodyType,
                            @RequestParam(name = "accountId") Integer accountId) {
        Map<String, Object> map = pgFacade.addZan(bodyId, bodyType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加关注", notes = "添加关注")
    public ModelAndView addMyConcern(@RequestParam(name = "token") String token,
                                     @RequestParam(name = "concernId") Integer concernId,
                                     @RequestParam(name = "concernType") Integer concernType,
                                     @RequestParam(name = "accountId") Integer accountId) {
        Map<String, Object> map = pgFacade.addMyConcern(concernId, concernType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/list/{token}/{max}", method = RequestMethod.GET)
    @ApiOperation(value = "圈子列表", notes = "圈子列表")
    public ModelAndView circleList(@PathVariable String token, @PathVariable Integer max) {
        Map<String, Object> map = pgFacade.circleList(max);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/random/{token}/{n}", method = RequestMethod.GET)
    @ApiOperation(value = "个人圈子列表", notes = "个人圈子列表")
    public ModelAndView random(@PathVariable String token, @PathVariable Integer n) {
        Map<String, Object> map = pgFacade.starRandom(1, n);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/starRandom/{token}/{n}", method = RequestMethod.GET)
    @ApiOperation(value = "明星圈子列表", notes = "明星圈子列表")
    public ModelAndView starRandom(@PathVariable String token, @PathVariable Integer n) {
        Map<String, Object> map = pgFacade.starRandom(3, n);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circleItem/add", method = RequestMethod.POST)
    @ApiOperation(value = "发布帖子", notes = "发布帖子")
    public ModelAndView addCircleItem(@RequestParam(name = "token") String token,
                                      @RequestParam(name = "circle_id") Integer circle_id,
                                      @RequestParam(name = "create_id") Integer create_id,
                                      @RequestParam(name = "title") String title,
                                      @RequestParam(name = "content") String content) {
        Map<String, Object> map = pgFacade.addCircleItem(circle_id, create_id, title, content);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/setMaster", method = RequestMethod.POST)
    @ApiOperation(value = "设置圈主", notes = "设置圈主")
    public ModelAndView setMaster(@RequestParam(name = "token") String token,
                                  @RequestParam(name = "circle_id") Integer circle_id,
                                  @RequestParam(name = "master_id") Integer master_id,
                                  @RequestParam(name = "account_id") Integer account_id) {
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

    @RequestMapping(value = "/v1/circleItem/list/{token}/{max}/{circleId}", method = RequestMethod.GET)
    @ApiOperation(value = "帖子列表", notes = "帖子列表")
    public ModelAndView circleItemList(@PathVariable String token, @PathVariable Integer max, @PathVariable Integer circleId) {
        Map<String, Object> map = pgFacade.circleItemList(max, circleId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/v1/circleItem/setTop/{token}/{circle_id}", method = RequestMethod.PUT)
    @ApiOperation(value = "设置置顶帖子", notes = "设置置顶帖子")
    public ModelAndView setTop(@PathVariable String token, @PathVariable Integer circle_id) {
        Map<String, Object> map = pgFacade.setTop(circle_id);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circleItem/top/{token}/{max}/{circleId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取置顶帖子列表", notes = "获取置顶帖子列表")
    public ModelAndView top(@PathVariable String token, @PathVariable Integer max, @PathVariable Integer circleId) {
        Map<String, Object> map = pgFacade.top(max, circleId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/reply/addReply", method = RequestMethod.POST)
    @ApiOperation(value = "发表回复", notes = "发表回复")
    public ModelAndView addReply(@RequestParam("token") String token,
                                 @RequestParam("reply_type") Integer reply_type,
                                 @RequestParam("reply_id") Integer reply_id,
                                 @RequestParam("account_id") Integer account_id,
                                 @RequestParam("content") String content) {
        Map<String, Object> map = pgFacade.addReply(reply_type, reply_id, account_id, content);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

//    @RequestMapping(value = "/v1/circle/queryCircleDeva/{token}", method = RequestMethod.GET)
//    @ApiOperation(value = "圈子首推数据", notes = "圈子首推数据")
//    public ModelAndView queryCircleDeva(@PathVariable String token) {
//        List<Circle> list = pgFacade.queryCircleDeva();
//        Map<String, Object> returnMap = new HashMap<>();
//        returnMap.put(Constants.STATE, Constants.SUCCESS);
//        returnMap.put(Constants.DATA, list);
//        AbstractView jsonView = new MappingJackson2JsonView();
//        jsonView.setAttributesMap(returnMap);
//        return new ModelAndView(jsonView);
//    }

//    @RequestMapping(value = "/v1/concern/queryConcernDeva/{token}", method = RequestMethod.GET)
//    @ApiOperation(value = "动态首推数据", notes = "动态首推数据")
//    public ModelAndView queryConcernDeva(@PathVariable String token) {
//        List<Concern> list = pgFacade.queryConcernDeva();
//        Map<String, Object> returnMap = new HashMap<>();
//        returnMap.put(Constants.STATE, Constants.SUCCESS);
//        returnMap.put(Constants.DATA, list);
//        AbstractView jsonView = new MappingJackson2JsonView();
//        jsonView.setAttributesMap(returnMap);
//        return new ModelAndView(jsonView);
//    }

    @RequestMapping(value = "/v1/circle/getOne/{circleId}/{accountId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取圈子单条数据", notes = "根据圈子id，用户id查询，范围帖子数，关注数，是否关注等信息")
    public ModelAndView queryConcernDeva(@PathVariable Integer circleId, @PathVariable Integer accountId) {
        Map<String, Object> returnMap = pgFacade.findCircle(circleId, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }


}
