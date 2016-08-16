package com.zyx.controller.activity;


import com.zyx.constants.Constants;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.utils.ActivityUtils;
import com.zyx.utils.FileUploadUtils;
import com.zyx.utils.ImagesVerifyUtils;
import com.zyx.entity.activity.parm.QueryActivityParm;
import com.zyx.entity.activity.parm.QueryHistoryParm;
import com.zyx.rpc.activity.ActivityFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rainbow on 16-6-13.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.activity.controller
 */
@RestController
@RequestMapping("/v1/activity")
public class ActivityController {

    @Resource
    private ActivityFacade activityFacade;
    @Resource
    private AccountCommonFacade accountCommonFacade;

    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ApiOperation(value = "活动发布", notes = "活动发布")
    public ModelAndView release(@RequestParam(name = "token", required = true) String token,
                                @RequestParam(name = "createId", required = true) Integer createId,
                                @RequestParam(name = "title", required = true) String title,
                                @RequestParam(name = "desc", required = true) String desc,
                                @RequestParam(name = "descimage", required = false) String[] descimage,
                                @RequestPart(name = "image", required = true) MultipartFile image,
                                @RequestParam(name = "startTime", required = true) Long startTime,
                                @RequestParam(name = "endTime", required = true) Long endTime,
                                @RequestParam(name = "lastTime", required = true) Long lastTime,
                                @RequestParam(name = "maxPeople", required = true) Integer maxPeople,
                                @RequestParam(name = "visible", required = true) Integer visible,
                                @RequestParam(name = "phone", required = true) String phone,
                                @RequestParam(name = "price", required = false) Double price,
                                @RequestParam(name = "type", required = true) Integer type,
                                @RequestParam(name = "address", required = false) String address,
                                @RequestParam(name = "examine", required = false) Integer examine,
                                @RequestParam(name = "memberTemplate", required = false) String memberTemplate) {

        AbstractView jsonView = new MappingJackson2JsonView();

        boolean token1 = accountCommonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());


        String uploadFile = FileUploadUtils.uploadFile(image);

        Map<String, Object> verify = ImagesVerifyUtils.verify(uploadFile);
        if (descimage != null && descimage.length >= 0) {
            String htmlImage = "";
            for (String s : descimage) {
                String html = "<img src=http://image.tiyujia.com/" + s + "/><br/>";
                htmlImage += html;
            }
            desc = desc + "<br/>" + htmlImage;
        }
        if (verify != null) {
            jsonView.setAttributesMap(verify);
            return new ModelAndView(jsonView);
        } else {
            Map<String, Object> map = activityFacade.insertActivity(createId, title, desc, uploadFile, startTime, endTime, lastTime, maxPeople, visible, phone, price, type, address, examine, memberTemplate);
            jsonView.setAttributesMap(map);
            return new ModelAndView(jsonView);
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "活动查询", notes = "活动查询")
    public ModelAndView query(@RequestParam(name = "token", required = true) String token,
                              @RequestParam(name = "createId", required = false) Integer createId,
                              @RequestParam(name = "id", required = false) Integer id,
                              @RequestParam(name = "groupsName", required = false) String groupName,
                              @RequestParam(name = "startTime", required = false) Long startTime,
                              @RequestParam(name = "endTime", required = false) Long endTime,
                              @RequestParam(name = "pageNumber", required = true) Integer pageNumber,
                              @RequestParam(name = "page", required = true) Integer page,
                              @RequestParam(name = "editState", required = false) Integer editState) {


        AbstractView jsonView = new MappingJackson2JsonView();

        boolean token1 = accountCommonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());

        QueryActivityParm parm = new QueryActivityParm();
        parm.setCreateId(createId);
        parm.setGroupName(groupName);
        parm.setId(id);
        parm.setStartTime(startTime);
        parm.setEndTime(endTime);
        parm.setPageNumber(pageNumber);
        parm.setPage(page);
        parm.setEditState(editState != null ? editState : 0);

        Map<String, Object> map = activityFacade.queryActivity(parm);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/memberTemplate", method = RequestMethod.POST)
    @ApiOperation(value = "活动报名模板查询", notes = "活动报名模板查询")
    public ModelAndView memberTemplate(@RequestParam(name = "token", required = true) String token,
                                       @RequestParam(name = "id", required = true) Integer activitiId) {


        AbstractView jsonView = new MappingJackson2JsonView();

        boolean token1 = accountCommonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());

        Map<String, Object> map = activityFacade.queryActivityMember(activitiId);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/queryGroupName", method = RequestMethod.POST)
    @ApiOperation(value = "活动组合查询", notes = "活动组合查询")
    public ModelAndView queryGroupName(@RequestParam(name = "name") String name) {


        AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> map = activityFacade.queryActivityGroupName(name);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    @ApiOperation(value = "查询历史活动", notes = "查询历史活动")
    public ModelAndView history(@RequestParam(name = "token", required = true) String token,
                                @RequestParam(name = "pageNumber", required = true) Integer pageNumber,
                                @RequestParam(name = "page", required = true) Integer page) {


        AbstractView jsonView = new MappingJackson2JsonView();

        boolean token1 = accountCommonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());

        QueryHistoryParm parm = new QueryHistoryParm();
        parm.setPageNumber(pageNumber);
        parm.setPageHis(page);

        Map<String, Object> map = activityFacade.queryActivityHistory(parm);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    /*@RequestMapping(value = "/testUpload", method = RequestMethod.POST)
    public void testUpload(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name = "image", required = false) MultipartFile[] file) {

        String images = "";
        if (file != null) {
            for (MultipartFile multipartFile : file) {
                images = images + FileUploadUtils.uploadFile(multipartFile) + ",";
            }
        }
        images = images.substring(0,images.length() - 1);
        //String uploadFile = FileUploadUtils.uploadFile(file);
        System.out.println();
    }*/


}
