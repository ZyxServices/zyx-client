package com.zyx.controller.activity;

import com.zyx.entity.activity.parm.MemberInfoParm;
import com.zyx.rpc.activity.ActivityMemberFacade;
import com.zyx.entity.activity.parm.QueryMemberParm;
import io.swagger.annotations.ApiOperation;
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
 * Created by Rainbow on 16-6-14.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.controller.activity
 */
@RestController
@RequestMapping("/v1/activity")
public class ActivityMemberController {

    @Resource
    private ActivityMemberFacade activityMemberFacade;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ApiOperation(value = "活动接口", notes = "活动报名")
    public ModelAndView signup(@RequestParam(name = "token", required = false) String token,
                               @RequestParam(name = "activityId", required = true) Integer activitiId,
                               @RequestParam(name = "userId", required = true) Integer userId,
                               @RequestParam(name = "userNick", required = true) String userNick,
                               @RequestParam(name = "phone", required = true) String phone,
                               @RequestParam(name = "memberInfo", required = true) String memberInfo) {


        AbstractView jsonView = new MappingJackson2JsonView();

        MemberInfoParm addMemberInfoParm = new MemberInfoParm();

        addMemberInfoParm.setActivityId(activitiId);
        addMemberInfoParm.setUserId(userId);
        addMemberInfoParm.setUserNick(userNick);
        addMemberInfoParm.setPhone(phone);
        addMemberInfoParm.setMemberInfo(memberInfo);

        Map<String, Object> map = activityMemberFacade.addActivityMember(addMemberInfoParm);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/cancelSignup", method = RequestMethod.POST)
    @ApiOperation(value = "活动接口", notes = "取消对应到活动报名")
    public ModelAndView cancelSignup(@RequestParam(name = "token", required = false) String token,
                                     @RequestParam(name = "activityId", required = true) Integer activitiId,
                                     @RequestParam(name = "userId", required = true) Integer userId) {


        AbstractView jsonView = new MappingJackson2JsonView();

        MemberInfoParm memberInfoParm = new MemberInfoParm();
        memberInfoParm.setActivityId(activitiId);
        memberInfoParm.setUserId(userId);

        Map<String, Object> map = activityMemberFacade.delActivityMember(memberInfoParm);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/memberPeople", method = RequestMethod.POST)
    @ApiOperation(value = "活动接口", notes = "查询参与活动人列表（详细信息）可查寻当前用户参加过到活动")
    public ModelAndView memberPeople(@RequestParam(name = "token", required = false) String token,
                                     @RequestParam(name = "activityId", required = false) Integer activitiId,
                                     @RequestParam(name = "userId", required = false) Integer userId) {


        AbstractView jsonView = new MappingJackson2JsonView();

        QueryMemberParm queryMemberParm = new QueryMemberParm();

        queryMemberParm.setActivityId(activitiId);
        queryMemberParm.setUserId(userId);

        Map<String, Object> map = activityMemberFacade.queryActivityMember(queryMemberParm);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "活动接口", notes = "发起者审核报名用户")
    public ModelAndView update(@RequestParam(name = "token", required = false) String token,
                                     @RequestParam(name = "id", required = true) Integer id) {

        AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> map = activityMemberFacade.updateMemberByExamine(id);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
