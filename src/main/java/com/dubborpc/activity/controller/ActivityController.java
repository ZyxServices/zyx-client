package com.dubborpc.activity.controller;

import com.dubborpc.activity.ActivityFacade;
import com.zyx.entity.activity.parm.QueryActivityParm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityFacade activityFacade;


    @RequestMapping(value = "/release", method = RequestMethod.GET)
    public ModelAndView release(@RequestParam(name = "token", required = false) String token,
                                  @RequestParam(name = "createId", required = false) Integer createId,
                                  @RequestParam(name = "title", required = false) String title,
                                  @RequestParam(name = "desc", required = false) String desc,
                                  @RequestParam(name = "image", required = false) String image,
                                  @RequestParam(name = "startTime", required = false) Long startTime,
                                  @RequestParam(name = "endTime", required = false) Long endTime,
                                  @RequestParam(name = "lastTime", required = false) Long lastTime,
                                  @RequestParam(name = "maxPeople", required = false) Integer maxPeople,
                                  @RequestParam(name = "visible", required = false) Integer visible,
                                  @RequestParam(name = "phone", required = false) String phone,
                                  @RequestParam(name = "price", required = false) Double price,
                                  @RequestParam(name = "type", required = false) Integer type,
                                  @RequestParam(name = "address", required = false) String address,
                                  @RequestParam(name = "examine", required = false) Integer examine,
                                  @RequestParam(name = "memberTemplate", required = false) String memberTemplate) {

            AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> map = activityFacade.insertActivity(createId, title, desc, image, startTime, endTime, lastTime, maxPeople, visible, phone, price, type, address, examine, memberTemplate);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(name = "token", required = false) String token,
                                  @RequestParam(name = "createId", required = false) Integer createId,
                                  @RequestParam(name = "id", required = false) Integer id,
                                  @RequestParam(name = "groupsName", required = false) String groupName,
                                  @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                  @RequestParam(name = "page", required = false) Integer page) {

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
}
