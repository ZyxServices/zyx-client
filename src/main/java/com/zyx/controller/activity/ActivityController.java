package com.zyx.controller.activity;


import com.utils.FileUploadUtils;
import com.utils.ImagesVerifyUtils;
import com.zyx.entity.activity.parm.QueryActivityParm;
import com.zyx.rpc.activity.ActivityFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/release", method = RequestMethod.GET)
    public ModelAndView release(@RequestParam(name = "token", required = false) String token,
                                @RequestParam(name = "createId", required = true) Integer createId,
                                @RequestParam(name = "title", required = true) String title,
                                @RequestParam(name = "desc", required = true) String desc,
                                @RequestParam(name = "image", required = true) MultipartFile image,
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

        String uploadFile = FileUploadUtils.uploadFile(image);

        Map<String, Object> verify = ImagesVerifyUtils.verify(uploadFile);

        if (verify != null) {
            jsonView.setAttributesMap(verify);
            return new ModelAndView(jsonView);
        } else {
            Map<String, Object> map = activityFacade.insertActivity(createId, title, desc, uploadFile, startTime, endTime, lastTime, maxPeople, visible, phone, price, type, address, examine, memberTemplate);
            jsonView.setAttributesMap(map);
            return new ModelAndView(jsonView);
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(name = "token", required = false) String token,
                              @RequestParam(name = "createId", required = false) Integer createId,
                              @RequestParam(name = "id", required = false) Integer id,
                              @RequestParam(name = "groupsName", required = false) String groupName,
                              @RequestParam(name = "pageNumber", required = true) Integer pageNumber,
                              @RequestParam(name = "page", required = true) Integer page) {


        AbstractView jsonView = new MappingJackson2JsonView();

        QueryActivityParm parm = new QueryActivityParm();
        parm.setCreateId(createId);
        parm.setGroupName(groupName);
        parm.setId(id);
        parm.setPageNumber(pageNumber);
        parm.setPage(page);

        Map<String, Object> map = activityFacade.queryActivity(parm);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/memberTemplate", method = RequestMethod.GET)
    public ModelAndView memberTemplate(@RequestParam(name = "token", required = false) String token,
                                       @RequestParam(name = "id", required = true) Integer activitiId) {


        AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> map = activityFacade.queryActivityMember(activitiId);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/testUpload", method = RequestMethod.POST)
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
    }


}
