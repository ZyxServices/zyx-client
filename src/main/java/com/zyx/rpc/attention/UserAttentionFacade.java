package com.zyx.rpc.attention;

import com.zyx.param.attention.AttentionParam;

import java.util.Map;

/**
 * Created by wms on 2016/8/16.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
public interface UserAttentionFacade {

    /**
     * 关注、拉黑
     */
    Map<String, Object> attentionFromAToB(AttentionParam attentionParam);

    /**
     * 取消关注，取消拉黑
     */
    Map<String, Object> unAttentionFromAToB(AttentionParam attentionParam);

    /**
     * 我的粉丝列表
     */
    Map<String, Object> myFSList(AttentionParam attentionParam);

    /**
     * 我的关注列表
     */
    Map<String, Object> myGZList(AttentionParam attentionParam);

    /**
     * 我的关注大咖列表
     */
    Map<String, Object> myDKGZList(AttentionParam attentionParam);

    /**
     * 检查用户A是否已经关注用户B
     */
    Map<String, Object> checkAttentionFromAToB(AttentionParam param);
}
