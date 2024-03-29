package com.zyx.rpc.live;

import java.util.List;
import java.util.Map;

import com.zyx.entity.live.Barrage;
import com.zyx.param.live.BarrageParam;
import com.zyx.vo.live.BarrageVo;

public interface BarrageFacade {
	/**
	 * 
	 * @param barrage
	 * @description T添加直播 需要Token 验证
	 * @version 1.0
	 * @author MrDeng
	 * @update 2016年6月22日 上午10:28:48
	 */
	public void add(Barrage barrage);
//	/**
//	 * 
//	 * @param id
//	 * @return
//	 * @description 获取直播 单个
//	 * @version 1.0
//	 * @author MrDeng
//	 * @update 2016年6月22日 上午10:30:44
//	 */
//	public Barrage getById(Long id);
	/**
	 * 
	 * @param param
	 * @return
	 * @description 获取直播列表
	 * @version 1.0
	 * @author MrDeng
	 * @update 2016年6月22日 上午10:30:21
	 */
	public List<Barrage> getLast(BarrageParam param);



	public List<BarrageVo> getList(BarrageParam param);
//	/**
//	 * 
//	 * @param token
//	 * @param id
//	 * @description 删除直播 需要Token验证
//	 * @version 1.0
//	 * @author MrDeng
//	 * @update 2016年6月22日 上午10:30:06
//	 */
//	public void delete(Long id);
}
