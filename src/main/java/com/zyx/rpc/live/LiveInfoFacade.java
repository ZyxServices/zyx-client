package com.zyx.rpc.live;

import java.util.List;

import com.zyx.entity.live.LiveInfo;
import com.zyx.vo.live.LiveInfoVo;

/**
 * 
 * @title LiveInfoFacade.java
 * @package com.zyx.rpc.live
 * @description TODO
 * @author DengQingHai   
 * @update 2016年6月14日 下午3:17:07
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
public interface LiveInfoFacade {
	public void add(LiveInfo liveInfo);
	public void updateNotNull(LiveInfo liveInfo);
	public LiveInfo getById(Long id);
	public List<LiveInfo> getList( LiveInfoVo liveInfoVo);
	public void delete(Long id);
}
