package com.zyx.rpc.attention;

import com.zyx.param.attention.AttentionParam;

import java.util.Map;

/**
 * Created by wms on 2016/8/16.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title UserAttentionFacade.java
 */
public interface UserAttentionFacade {

    /**
     * 关注、拉黑
     *
     * @param attentionParam
     * @return
     */
    Map<String, Object> attentionFromAToB(AttentionParam attentionParam);

    /**
     * 我的粉丝列表
     *
     * @param attentionParam
     * @return
     */
    Map<String, Object> myFSList(AttentionParam attentionParam);

    /**
     * 我的关注列表
     *
     * @param attentionParam
     * @return
     */
    Map<String, Object> myGZList(AttentionParam attentionParam);
}
