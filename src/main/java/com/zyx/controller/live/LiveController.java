package com.zyx.controller.live;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.fastjson.JSON;
import com.zyx.constants.account.AccountConstants;
import com.zyx.constants.live.LiveConstants;
import com.zyx.entity.live.Barrage;
import com.zyx.entity.live.LiveInfo;
import com.zyx.entity.live.TextLiveItem;
import com.zyx.rpc.account.AccountInfoFacade;
import com.zyx.rpc.live.BarrageFacade;
import com.zyx.rpc.live.LiveInfoFacade;
import com.zyx.rpc.live.TextLiveItemFacade;
import com.zyx.vo.account.AccountInfoVo;
import com.zyx.vo.common.TimeAreaVo;
import com.zyx.vo.live.BarrageVo;
import com.zyx.vo.live.LiveInfoVo;
import com.zyx.vo.live.LiveSearchVo;
import com.zyx.vo.live.TextLiveItemVo;

/**
 * 
 * @title LiveController.java
 * @package com.zyx.controller
 * @description TODO
 * @author DengQingHai
 * @update 2016年6月14日 下午3:17:26
 * @version V1.0 Copyright (c)2012 chantsoft-版权所有
 */
@RestController
@RequestMapping("/v1/live")
public class LiveController {

	@Autowired
	LiveInfoFacade liveInfoFacade;
	@Autowired
	TextLiveItemFacade textLiveItemFacade;
	@Autowired
	BarrageFacade barrageFacade;
	@Autowired
	AccountInfoFacade accountInfoFacade;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createLive(HttpServletRequest request) {
		// Token 验证
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("token") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (request.getParameter("type") == null || request.getParameter("title") == null
				|| request.getParameter("lab") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				LiveInfo liveInfo = new LiveInfo();
				// 系统补全参数
				liveInfo.setCreateTime(System.currentTimeMillis());
				// 传入参数构造
				if (request.getParameter("isPublic") == null)
					liveInfo.setPublic(true);
				else
					liveInfo.setPublic(Boolean.parseBoolean(request.getParameter("isPublic")));
				if (request.getParameter("start") == null)
					liveInfo.setStart(liveInfo.getCreateTime());
				else
					liveInfo.setStart(Long.parseLong(request.getParameter("start")));
				liveInfo.setType(Integer.parseInt(request.getParameter("type")));
				liveInfo.setStart(Long.parseLong(request.getParameter("end")));
				liveInfo.setLab(Integer.parseInt(request.getParameter("lab")));
				liveInfo.setTitle(request.getParameter("title"));
				liveInfo.setUserId(Long.parseLong(request.getParameter("userId")));

				liveInfoFacade.add(request.getParameter("token"), liveInfo);
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateLive(HttpServletRequest request) {

		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("token") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else {
			try {
				LiveInfo liveInfo = new LiveInfo();
				// 传入参数构造
				liveInfo.setId(Long.parseLong(request.getParameter("id")));
				liveInfo.setPublic(Boolean.parseBoolean(request.getParameter("isPublic")));
				liveInfo.setType(Integer.parseInt(request.getParameter("type")));
				liveInfo.setStart(Long.parseLong(request.getParameter("start")));
				liveInfo.setStart(Long.parseLong(request.getParameter("end")));
				liveInfo.setLab(Integer.parseInt(request.getParameter("lab")));
				liveInfo.setTitle(request.getParameter("title"));
				liveInfo.setUserId(Long.parseLong(request.getParameter("userId")));
				// 系统补全参数
				liveInfoFacade.updateNotNull(request.getParameter("token"), liveInfo);
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView getLiveList(HttpServletRequest request) {

		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		try {
			LiveInfoVo liveInfoVo = new LiveInfoVo();
			liveInfoVo.setIds(JSON.parseArray(request.getParameter("ids"), Integer.class));

			if (liveInfoVo.getIds() == null || liveInfoVo.getIds().isEmpty()) {// 未指定ID时
				if (request.getParameter("createTime") != null)
					liveInfoVo.setCreateTime(JSON.parseObject(request.getParameter("createTime"), TimeAreaVo.class));
				if (request.getParameter("start") != null)
					liveInfoVo.setStart(JSON.parseObject(request.getParameter("start"), TimeAreaVo.class));
				if (request.getParameter("end") != null)
					liveInfoVo.setEnd(JSON.parseObject(request.getParameter("end"), TimeAreaVo.class));
				if (request.getParameter("labs") != null)
					liveInfoVo.setLabs(JSON.parseArray(request.getParameter("labs"), Integer.class));
				if (request.getParameter("type") != null)
					liveInfoVo.setType(Integer.parseInt(request.getParameter("type")));
				if (request.getParameter("userID") != null)
					liveInfoVo.setUserId(Long.parseLong(request.getParameter("userID")));
			}

			List<LiveInfo> list = liveInfoFacade.getList(liveInfoVo);
			attrMap.put("liveInfos", JSON.toJSONString(list));
			attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
		} catch (Exception e) {
			e.printStackTrace();
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchLiveList(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		try {
			LiveSearchVo liveSearchVo = new LiveSearchVo();
			if (request.getParameter("lab") != null)
				liveSearchVo.setLab(request.getParameter("lab"));
			if (request.getParameter("name") != null)
				liveSearchVo.setName(request.getParameter("userName"));
			if (request.getParameter("keyWord") != null)
				liveSearchVo.setKeyWord(request.getParameter("keyWord"));
			List<LiveInfo> list = liveInfoFacade.searchList(liveSearchVo);
			attrMap.put("liveInfos", JSON.toJSONString(list));
			attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
		} catch (Exception e) {
			e.printStackTrace();
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public ModelAndView getLiveByKey(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("id") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				LiveInfo liveInfo = liveInfoFacade.getById(Long.parseLong(request.getParameter("id")));
				attrMap.put("liveInfo", JSON.toJSONString(liveInfo));
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView deleteLiveByKey(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("token") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (request.getParameter("id") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				liveInfoFacade.delete(request.getParameter("token"), Long.parseLong(request.getParameter("id")));
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	///////////
	////
	///////////

	@RequestMapping(value = "/text/create", method = RequestMethod.POST)
	public ModelAndView createTextLiveItem(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("token") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (request.getParameter("liveId") == null
				|| !(request.getParameter("content") != null || request.getParameter("imgUrl") != null)) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			TextLiveItem item = new TextLiveItem();
			try {
				// 传入参数构造
				item.setLiveId(Long.parseLong(request.getParameter("liveId")));
				item.setContent(request.getParameter("content"));
				item.setImgUrl(request.getParameter("imgUrl"));
				item.setCreateTime(System.currentTimeMillis());
				textLiveItemFacade.add(item);
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @description 直播内容不提供修改
	 * @version 1.0
	 * @author MrDeng
	 * @update 2016年6月15日 下午2:37:48
	 */
	// @RequestMapping(value = "/update", method = RequestMethod.POST)
	// public void updateTextLiveItem(HttpServletRequest request) {
	// TextLiveItem item = new TextLiveItem();
	// try {
	// // 传入参数构造
	// item.setId(Long.parseLong(request.getParameter("id")));
	// item.setLiveId(Long.parseLong(request.getParameter("liveId")));
	// item.setContent(request.getParameter("content"));
	// item.setImgUrl(request.getParameter("imgUrl"));
	// item.setCreateTime(System.currentTimeMillis());
	// // 系统补全参数
	// } catch (NumberFormatException nfe) {
	//
	// }
	//
	// }

	@RequestMapping(value = "/text/list", method = RequestMethod.POST)
	public ModelAndView getTextLiveItemList(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("liveId") == null && request.getParameter("createTime") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				TextLiveItemVo vo = new TextLiveItemVo();
				if (request.getParameter("liveId") != null)
					vo.setLiveId(Long.parseLong(request.getParameter("liveId")));
				if (request.getParameter("createTime") != null)
					vo.setCreateTime(JSON.parseObject(request.getParameter("createTime"), TimeAreaVo.class));
				List<TextLiveItem> list = textLiveItemFacade.getList(vo);
				attrMap.put("textLiveItems", JSON.toJSONString(list));
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/text/get", method = RequestMethod.POST)
	public ModelAndView getTextLiveItemByKey(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("id") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				TextLiveItem textLiveItem = textLiveItemFacade.getById(Long.parseLong(request.getParameter("id")));
				attrMap.put("textLiveItem", JSON.toJSONString(textLiveItem));
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}

		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/text/delete", method = RequestMethod.POST)
	public ModelAndView deleteTextLiveItemByKey(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("token") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (request.getParameter("id") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				textLiveItemFacade.deleteById(Long.parseLong(request.getParameter("id")));
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/barrage/create", method = RequestMethod.POST)
	public ModelAndView createBarrage(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("token") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (request.getParameter("liveId") == null || request.getParameter("userId") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else if (request.getParameter("content") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.LIVE_BARRAGE_NULL_CONTENT);
		} else {

			try {
				Map<String, Object> rstMap = accountInfoFacade.queryAccountInfo(request.getParameter("token"),
						Integer.parseInt(request.getParameter("token")));

				AccountInfoVo account = (AccountInfoVo) rstMap.get("result");
				if (null == account) {
					attrMap.put(LiveConstants.ERROR_CODE, AccountConstants.ACCOUNT_ERROR_CODE_50300);
				} else {
					// 传入参数构造
					Barrage entity = new Barrage();
					entity.setLiveId(Long.parseLong(request.getParameter("liveId")));
					entity.setContent(request.getParameter("content"));
					entity.setUserId(Integer.parseInt(account.getId()));
					entity.setNickName(account.getNickname());
					entity.setAvatar(account.getAvatar());
					entity.setCreateTime(System.currentTimeMillis());
					barrageFacade.add(request.getParameter("token"), entity);
					attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
				}
			} catch (NumberFormatException nfe) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/barrage/list", method = RequestMethod.POST)
	public ModelAndView getBarrageList(HttpServletRequest request) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (request.getParameter("liveId") == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				BarrageVo vo = new BarrageVo();
				if (null != request.getParameter("liveId"))
					vo.setLiveId(Long.parseLong(request.getParameter("liveId")));
				if (null != request.getParameter("createTime"))
					vo.setCreateTime(JSON.parseObject(request.getParameter("liveId"), TimeAreaVo.class));
				List<Barrage> list = barrageFacade.getLast(vo);
				attrMap.put("textLiveItems", JSON.toJSONString(list));
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

}
