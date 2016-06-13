package com.dubborpc.activity;

import com.zyx.entity.activity.parm.QueryActivityParm;

import java.util.Map;

/**
 * Created by Rainbow on 16-6-12.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.activity
 */
public interface ActivityFacade {
    /**
     * @param createId
     * @param title
     * @param desc
     * @param image
     * @param startTime
     * @param endTime
     * @param lastTime
     * @param maxPeople
     * @param visible
     * @param phone
     * @param price
     * @param type
     * @param address
     * @param examine
     * @param memberTemplate
     * @return
     */
    Map<String, Object> insertActivity(Integer createId, String title, String desc, String image, Long startTime,
                                       Long endTime, Long lastTime, Integer maxPeople, Integer visible,
                                       String phone, Double price, Integer type, String address, Integer examine,
                                       String memberTemplate);

    /**
     * 多条条件查询活动
     * @param parm
     * @return
     */
    Map<String, Object> queryActivity(QueryActivityParm parm);
}
