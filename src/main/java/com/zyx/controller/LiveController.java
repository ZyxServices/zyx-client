package com.zyx.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.RpcContext;
import com.zyx.common.enums.LiveLab;
import com.zyx.common.enums.LiveType;
import com.zyx.entity.live.LiveInfo;
import com.zyx.rpc.live.LiveInfoFacade;
import com.zyx.vo.live.LiveInfoVo;

/**
 * 
 * @title LiveController.java
 * @package com.zyx.controller
 * @description TODO
 * @author DengQingHai   
 * @update 2016年6月14日 下午3:17:26
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@RestController
@RequestMapping("/v1/live")
public class LiveController {

	@Autowired
	LiveInfoFacade liveInfoFacade;
	
	
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public void create(HttpServletRequest request) {
		LiveInfo liveInfo = new LiveInfo();
		try{
			//传入参数构造
			liveInfo.setPublic(Boolean.parseBoolean(request.getParameter("isPublic")));
			liveInfo.setType(Integer.parseInt(request.getParameter("type")));
			liveInfo.setStart(Long.parseLong(request.getParameter("start")));
			liveInfo.setStart(Long.parseLong(request.getParameter("end")));
			liveInfo.setLab(Integer.parseInt(request.getParameter("lab")));
			liveInfo.setTitle(request.getParameter("title"));
			liveInfo.setUserId(Long.parseLong(request.getParameter("userId")));
			//系统补全参数
			liveInfo.setCreateTime(System.currentTimeMillis());
			liveInfoFacade.add(liveInfo);
		}catch(NumberFormatException nfe){
			
		}
		
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public void update(HttpServletRequest request) {
		LiveInfo liveInfo = new LiveInfo();
		try{
			//传入参数构造
			liveInfo.setId(Long.parseLong(request.getParameter("id")));
			liveInfo.setPublic(Boolean.parseBoolean(request.getParameter("isPublic")));
			liveInfo.setType(Integer.parseInt(request.getParameter("type")));
			liveInfo.setStart(Long.parseLong(request.getParameter("start")));
			liveInfo.setStart(Long.parseLong(request.getParameter("end")));
			liveInfo.setLab(Integer.parseInt(request.getParameter("lab")));
			liveInfo.setTitle(request.getParameter("title"));
			liveInfo.setUserId(Long.parseLong(request.getParameter("userId")));
			//系统补全参数
			liveInfo.setCreateTime(System.currentTimeMillis());
			liveInfoFacade.update(liveInfo);
		}catch(NumberFormatException nfe){
			
		}
		
	}
	
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public List<LiveInfo> getList(HttpServletRequest request){
		LiveInfoVo liveInfoVo = new LiveInfoVo();
		liveInfoVo.setType(Integer.parseInt(request.getParameter("type")));
		List<LiveInfo> list = liveInfoFacade.getList( liveInfoVo);
		System.out.println("****get list size="+(null==list?0:list.size()));
		return list;
	}
	
	@RequestMapping(value = "/get",method = RequestMethod.POST)
	public LiveInfo getByKey(HttpServletRequest request){
		return liveInfoFacade.getById(Long.parseLong(request.getParameter("id")));
	}

}
