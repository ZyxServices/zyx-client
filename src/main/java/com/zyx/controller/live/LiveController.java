package com.zyx.controller.live;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.zyx.vo.live.BarrageVo;
import com.zyx.vo.live.LiveInfoVo;
import com.zyx.vo.live.LiveSearchVo;
import com.zyx.vo.live.TextLiveItemVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@Api(description = "直播相关接口")
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
	@ApiOperation(value = "直播发布", notes = "直播-直播发布")
	public ModelAndView createLive(@RequestParam(name = "token") String token,
			@RequestParam(name = "isPublic", required = false) Boolean isPublic,
			@RequestParam(name = "type") Integer type, @RequestParam(name = "start", required = false) Long start,
			@RequestParam(name = "end", required = false) Long end, @RequestParam(name = "userId") Long userId,
			@RequestParam(name = "title") String title, @RequestParam(name = "lab") Integer lab,
			@RequestParam(name = "bgmUrl", required = false) String bgmUrl,
			@RequestParam(name = "vedioUrl", required = false) String vedioUrl) {
		// Token 验证
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (token == null || "".equals(token)) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (type == null || title == null || lab == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				LiveInfo liveInfo = new LiveInfo();
				// 系统补全参数
				liveInfo.setCreateTime(System.currentTimeMillis());
				// 传入参数构造
				liveInfo.setPublic(isPublic == null ? true : isPublic);
				liveInfo.setType(type);
				liveInfo.setTitle(title);
				liveInfo.setUserId(userId);
				liveInfo.setLab(lab);
				// 不必须字段
				liveInfo.setStart(start == null ? System.currentTimeMillis() : start);
				liveInfo.setEnd(end == null ? System.currentTimeMillis() : end);
				liveInfo.setBgmUrl(bgmUrl);
				liveInfo.setVedioUrl(vedioUrl);
				liveInfoFacade.add(token, liveInfo);
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
	@ApiOperation(value = "直播更新修改", notes = "直播-直播更新修改")
	public ModelAndView updateLive(@RequestParam(name = "token") String token, @RequestParam(name = "id") Long id,
			@RequestParam(name = "isPublic", required = false) Boolean isPublic,
			@RequestParam(name = "type") Integer type, @RequestParam(name = "start", required = false) Long start,
			@RequestParam(name = "end", required = false) Long end, @RequestParam(name = "userId") Long userId,
			@RequestParam(name = "title") String title, @RequestParam(name = "lab") Integer lab,
			@RequestParam(name = "bgmUrl", required = false) String bgmUrl,
			@RequestParam(name = "vedioUrl", required = false) String vedioUrl) {

		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (token == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else {
			try {
				LiveInfo liveInfo = new LiveInfo();
				// 传入参数构造
				liveInfo.setCreateTime(System.currentTimeMillis());
				// 传入参数构造
				liveInfo.setPublic(isPublic == null ? true : isPublic);
				liveInfo.setType(type);
				liveInfo.setTitle(title);
				liveInfo.setUserId(userId);
				liveInfo.setLab(lab);
				// 不必须字段
				liveInfo.setStart(start == null ? System.currentTimeMillis() : start);
				liveInfo.setEnd(end == null ? System.currentTimeMillis() : end);
				liveInfo.setBgmUrl(bgmUrl);
				liveInfo.setVedioUrl(vedioUrl);
				// 系统补全参数
				liveInfoFacade.updateNotNull(token, liveInfo);
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
	@ApiOperation(value = "直播-获取多条直播", notes = "直播-获取多条直播")
	public ModelAndView getLiveList(@RequestParam(name = "ids", required = false) List<Long> ids,
			@RequestParam(name = "createTimeLower", required = false) Long createTimeLower,
			@RequestParam(name = "createTimeUpper", required = false) Long createTimeUpper,
			@RequestParam(name = "startUpper", required = false) Long startUpper,
			@RequestParam(name = "startUpper", required = false) Long startLower,
			@RequestParam(name = "endUpper", required = false) Long endUpper,
			@RequestParam(name = "endLower", required = false) Long endLower,
			@RequestParam(name = "type", required = false) Integer type,
			@RequestParam(name = "lab", required = false) List<Integer> labs) {

		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		try {
			LiveInfoVo liveInfoVo = new LiveInfoVo();
			if (null != ids && !ids.isEmpty()) {
				liveInfoVo.setIds(ids);
			} else {
				liveInfoVo.setCreateTimeLower(createTimeLower);
				liveInfoVo.setCreateTimeUpper(createTimeUpper);
				liveInfoVo.setStartLower(startLower);
				liveInfoVo.setStartUpper(startUpper);
				liveInfoVo.setEndLower(endLower);
				liveInfoVo.setEndUpper(endUpper);
				liveInfoVo.setType(type);
				liveInfoVo.setLabs(labs);
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
	@ApiOperation(value = "直播-直播模块搜索", notes = "直播-直播模块搜索")
	public ModelAndView searchLiveList(@RequestParam(name = "lab", required = false) Integer lab,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "keyWord", required = false) String keyWord) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		try {
			LiveSearchVo liveSearchVo = new LiveSearchVo();
			liveSearchVo.setLab(lab);
			liveSearchVo.setName(name);
			liveSearchVo.setKeyWord(keyWord);
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
	@ApiOperation(value = "直播-获取单个直播", notes = "直播-获取单个直播")
	public ModelAndView getLiveByKey(@RequestParam(name = "id") Long id) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (id == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				LiveInfo liveInfo = liveInfoFacade.getById(id);
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
		System.out.println(JSON.toJSONString(attrMap));
		return new ModelAndView(jsonView);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ApiOperation(value = "直播-删除直播", notes = "直播-删除直播")
	public ModelAndView deleteLiveByKey(@RequestParam(name = "token") String token,
			@RequestParam(name = "id") Long id) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (token == null || "".equals(token)) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (id == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				liveInfoFacade.delete(token, id);
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
	@ApiOperation(value = "直播-发布直播图文内容", notes = "直播-发布直播图文内容")
	public ModelAndView createTextLiveItem(@RequestParam(name = "token") String token,
			@RequestParam(name = "liveId") Long liveId, @RequestParam(name = "imgUrl", required = false) String imgUrl,
			@RequestParam(name = "content", required = false) String content) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (token == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (liveId == null || !(content != null || imgUrl != null)) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			TextLiveItem item = new TextLiveItem();
			try {
				// 传入参数构造
				item.setLiveId(liveId);
				item.setContent(content);
				item.setImgUrl(imgUrl);
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

	@RequestMapping(value = "/text/list", method = RequestMethod.POST)
	@ApiOperation(value = "直播-获取多条直播图文内容", notes = "直播-获取多条直播图文内容")
	public ModelAndView getTextLiveItemList(@RequestParam(name = "liveId", required = false) Long liveId,
			@RequestParam(name = "createTimeLower", required = false) Long createTimeLower,
			@RequestParam(name = "createTimeUpper", required = false) Long createTimeUpper) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (liveId == null && createTimeLower == null && createTimeUpper == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				TextLiveItemVo vo = new TextLiveItemVo();
				vo.setCreateTimeLower(createTimeLower);
				vo.setCreateTimeUpper(createTimeUpper);
				vo.setLiveId(liveId);
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
	@ApiOperation(value = "直播-获取单条直播图文内容", notes = "直播-获取单条直播图文内容")
	public ModelAndView getTextLiveItemByKey(@RequestParam(name = "id") Long id) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (id == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				TextLiveItem textLiveItem = textLiveItemFacade.getById(id);
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
	@ApiOperation(value = "直播-删除图文直播", notes = "直播-删除图文直播")
	public ModelAndView deleteTextLiveItemByKey(@RequestParam(name = "token") Long token,
			@RequestParam(name = "id") Long id) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (token == null || "".equals(token)) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (id == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				textLiveItemFacade.deleteById(id);
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
	@ApiOperation(value = "直播-发送直播弹", notes = "直播-发送直播弹幕")
	public ModelAndView createBarrage(@RequestParam(name = "token") String token,
			@RequestParam(name = "liveId") Long liveId, @RequestParam(name = "userId") Integer userId,
			@RequestParam(name = "content") String content) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (token == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.REQUEST_UNAUTHORIZED);
		} else if (liveId == null || liveId == null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else if (content == null || "".equals(content)) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.LIVE_BARRAGE_NULL_CONTENT);
		} else {

			try {
				Map<String, Object> rstMap = accountInfoFacade.queryAccountInfo(token, userId);
				AccountInfoVo account = (AccountInfoVo) rstMap.get("result");
				if (null == account) {
					attrMap.put(LiveConstants.ERROR_CODE, AccountConstants.ACCOUNT_ERROR_CODE_50300);
				} else {
					// 传入参数构造
					Barrage entity = new Barrage();
					entity.setLiveId(liveId);
					entity.setContent(content);
					entity.setUserId(account.getId());
					entity.setNickName(account.getNickname());
					entity.setAvatar(account.getAvatar());
					entity.setCreateTime(System.currentTimeMillis());
					barrageFacade.add(token, entity);
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
	@ApiOperation(value = "直播-获取直播弹幕", notes = "直播-获取直播弹幕")
	public ModelAndView getBarrageList(@RequestParam(name = "liveId", required = false) Long liveId,
			@RequestParam(name = "createTimeLower", required = false) Long createTimeLower,
			@RequestParam(name = "createTimeUpper", required = false) Long createTimeUpper) {
		Map<String, Object> attrMap = new HashMap<>();
		attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
		if (liveId== null) {
			attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_MISS);
		} else {
			try {
				BarrageVo vo = new BarrageVo();
				vo.setLiveId(liveId);
				vo.setCreateTimeLower(createTimeLower);
				vo.setCreateTimeUpper(createTimeUpper);
				List<Barrage> list = barrageFacade.getLast(vo);
				attrMap.put("textLiveItems", JSON.toJSONString(list));
				attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.PARAM_ILIGAL);
			} catch (Exception e) {
				e.printStackTrace();
				attrMap.put(LiveConstants.ERROR_CODE, LiveConstants.ERROR);
			}
		}
		AbstractView jsonView = new MappingJackson2JsonView();
		jsonView.setAttributesMap(attrMap);
		return new ModelAndView(jsonView);
	}

}
