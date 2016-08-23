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
import java.util.Objects;

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
                                  @RequestParam("tag") Integer tag,
                                  @RequestParam(value = "headImgUrl", required = false) String headImgUrl) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = pgFacade.insertCircle(title, createId, circleType, details, headImgUrl, tag);
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
                                @RequestParam(name = "imgUrl", required = false) String imgUrl,
                                @RequestParam(name = "videoUrl", required = false) String videoUrl,
                                @RequestParam(name = "visible") Integer visible) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = pgFacade.addCern(userId, type, cernTitle, content, imgUrl, videoUrl, visible);
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
                                      @RequestParam(name = "content") String content,
                                      @RequestParam(name = "img_url", required = false) String img_url) {
        Map<String, Object> map = pgFacade.addCircleItem(circle_id, create_id, title, content, img_url);
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

    @RequestMapping(value = "/v1/circleItem/list", method = RequestMethod.POST)
    @ApiOperation(value = "帖子列表", notes = "帖子列表")
    public ModelAndView circleItemList(@RequestParam(value = "token", required = false) String token,
                                       @RequestParam(value = "max") Integer max,
                                       @RequestParam(value = "circleId", required = false) Integer circleId) {
        Map<String, Object> map = pgFacade.circleItemList(max, circleId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/v1/circleItem/setTop", method = RequestMethod.POST)
    @ApiOperation(value = "设置置顶帖子", notes = "设置置顶帖子")
    public ModelAndView setTop(@RequestParam("token") String token,
                               @RequestParam("circle_id") Integer circle_id,
                               @RequestParam("topSize") Integer topSize) {
        Map<String, Object> map = pgFacade.setTop(topSize, circle_id);
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
    @ApiOperation(value = "获取圈子数据", notes = "根据圈子id，用户id查询，范围帖子数，关注数，是否关注等信息")
    public ModelAndView getOne(@PathVariable Integer circleId, @PathVariable Integer accountId) {
        Map<String, Object> returnMap = pgFacade.findCircle(circleId, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/cern/findParams/{token}/{concernId}/{concernType}", method = RequestMethod.GET)
    @ApiOperation(value = "关注列表条件查询", notes = "concernId，与concernType，自行去github查看")
    public ModelAndView findMyconcernParams(@PathVariable String token, @PathVariable Integer concernId, @PathVariable Integer concernType) {
        Map<String, Object> returnMap = pgFacade.findMyConcernParams(concernId, concernType);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circleItem/delete/{createId}/{circleItemId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除帖子", notes = "createId 户名id,circleItemId 帖子id")
    public ModelAndView deleteCircleItemByThisUser(
            @PathVariable("createId") Integer createId,
            @PathVariable("circleItemId") Integer circleItemId) {
        Map<String, Object> returnMap = pgFacade.deleteCircleItem(createId, circleItemId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/closeMaster", method = RequestMethod.POST)
    @ApiOperation(value = "取消圈主", notes = "circleId 圈子id,accountId 用户id")
    public ModelAndView closeMaster(
            @RequestParam(value = "circleId") Integer circleId,
            @RequestParam(value = "accountId") Integer accountId) {
        Map<String, Object> returnMap = pgFacade.closeMaster(circleId, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/updateImg", method = RequestMethod.POST)
    @ApiOperation(value = "修改圈子图片", notes = "circleId 圈子id,accountId 用户id")
    public ModelAndView updateCircleImg(
            @RequestParam(value = "circleId") Integer circleId,
            @RequestPart(value = "imgFile") MultipartFile imgFile) {
        AbstractView jsonView = new MappingJackson2JsonView();

        String imgDbUrl = null;
        if (!Objects.equals(imgFile, null)) {
            imgDbUrl = FileUploadUtils.uploadFile(imgFile);
            Map<String, Object> returnResult = ImagesVerifyUtils.verify(imgDbUrl);
            if (returnResult != null) {
                jsonView.setAttributesMap(returnResult);
                return new ModelAndView(jsonView);
            }
        }
        Map<String, Object> returnMap = pgFacade.updateCircleImg(imgDbUrl, circleId);
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/setAdminIds", method = RequestMethod.POST)
    @ApiOperation(value = "设置管理员", notes = "circleId 圈子id,accountId 当前操作用户id,adminIds 管理员id字符串，设置管理员与取消管理员同样适用")
    public ModelAndView setAdminIds(
            @RequestParam(value = "accountId") Integer accountId,
            @RequestParam(value = "adminIds") String adminIds,
            @RequestParam(value = "circleId") Integer circleId) {
        Map<String, Object> returnMap = pgFacade.setAdmins(accountId, adminIds, circleId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circle/jxCircle/{max}", method = RequestMethod.GET)
    @ApiOperation(value = "获取精选圈子", notes = "max为查询最大条数")
    public ModelAndView jxCircle(
            @PathVariable(value = "max") Integer max) {
        Map<String, Object> returnMap = pgFacade.jxCircle(max);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circleItem/lbCircleItem/{max}", method = RequestMethod.GET)
    @ApiOperation(value = "获取精选圈子模块帖子轮播图", notes = "max为查询最大条数")
    public ModelAndView lbCircleItem(
            @PathVariable(value = "max") Integer max) {
        Map<String, Object> returnMap = pgFacade.lbCircleItem(max);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/concern/getFollow/{loginUserId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据登录用户查询当前用户的关注的动态列表", notes = "loginUserId：登录用户id")
    public ModelAndView getFollow(
            @PathVariable(value = "loginUserId") Integer loginUserId) {
        Map<String, Object> returnMap = pgFacade.getMyFollowList(loginUserId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/concern/starConcern/{max}", method = RequestMethod.GET)
    @ApiOperation(value = "大咖动态", notes = "max：最大条数")
    public ModelAndView starConcern(
            @PathVariable(value = "max") Integer max) {
        Map<String, Object> returnMap = pgFacade.starConcern(max);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/concern/getOne/{token}/{concernId}", method = RequestMethod.GET)
    @ApiOperation(value = "动态详情", notes = "concernId：动态id")
    public ModelAndView getOneConcern(
            @PathVariable(value = "token") String token,
            @PathVariable(value = "concernId") Integer concernId) {
        Map<String, Object> returnMap = pgFacade.getOneConcern(concernId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circleItem/getOne/{token}/{circleItemId}", method = RequestMethod.GET)
    @ApiOperation(value = "帖子详情", notes = "circleItem：帖子id")
    public ModelAndView getOneCircleItem(
            @PathVariable(value = "token") String token,
            @PathVariable(value = "circleItemId") Integer circleItemId) {
        Map<String, Object> returnMap = pgFacade.getOneCircleItem(circleItemId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/circleItem/getTjCircleItem/{token}/{start}/{pageSize}", method = RequestMethod.GET)
    @ApiOperation(value = "帖子推荐列表", notes = "max：最大条数")
    public ModelAndView getTjCircleItem(
            @PathVariable(value = "token") String token,
            @PathVariable(value = "start") Integer start,
            @PathVariable(value = "pageSize") Integer pageSize) {
        Map<String, Object> returnMap = pgFacade.getTjCircleItem(start, pageSize);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }


}
