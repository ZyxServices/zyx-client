package com.zyx.rpc.pg;

import com.zyx.entity.pg.Circle;
import com.zyx.entity.pg.Concern;

import java.util.List;
import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.dubborpc.pg
 * Create by XiaoWei on 2016/6/14
 */
public interface PgFacade {
    /**
     * 圈子签到
     *
     * @param circleId
     * @param accountId
     * @return
     */
    Map<String, Object> addMeet(Integer circleId, Integer accountId);

    /**
     * 发布圈子
     *
     * @param title      圈子标题
     * @param createId   圈子创建者id
     * @param details    内容
     * @param headImgUrl 圈子头像url
     */
    Map<String, Object> insertCircle(String title, Integer createId, Integer circleType, String details, String headImgUrl, Integer tag);


    /**
     * 发布动态(多模块)
     *
     * @param userId
     * @param cernTitle
     * @param content
     * @param cernImgurl
     * @param videoUrl
     * @param visible
     * @return
     */
    Map<String, Object> addCern(Integer userId, Integer type, String cernTitle, String content, String cernImgurl, String videoUrl, Integer visible);


    /**
     * 各模块点赞
     *
     * @param body_id
     * @param body_type
     * @param account_id
     * @return
     */
    Map<String, Object> addZan(Integer body_id, Integer body_type, Integer account_id);

    /**
     * 各主体添加关注
     *
     * @param concernId    主体id
     * @param concern_type 主体类型
     * @return
     * @accountId 添加关注的人
     */
    Map<String, Object> addMyConcern(Integer concernId, Integer concern_type, Integer accountId);

    /**
     * 圈子列表
     *
     * @param max 指定最大条数为多少
     * @return
     */
    Map<String, Object> circleList(Integer max);

    /**
     * 明星动态随机n条数据
     *
     * @param n
     * @return
     * @type 类型，1为个人，2为活动，3为明星
     */
    Map<String, Object> starRandom(Integer type, Integer n);

    /**
     * 圈子发布帖子
     *
     * @param circle_id
     * @param create_id
     * @param title
     * @param content
     * @return
     */
    Map<String, Object> addCircleItem(Integer circle_id, Integer create_id, String title, String content);

    /**
     * 圈子设置圈主
     *
     * @param circle_id
     * @param master_id
     * @param account_id
     * @return
     */
    Map<String, Object> setMaster(Integer circle_id, Integer master_id, Integer account_id);

    /**
     * 删除圈子
     *
     * @param circle_id
     * @return
     */
    Map<String, Object> delete(Integer circle_id);

    /**
     * 帖子列表
     *
     * @param max
     * @return
     */
    Map<String, Object> circleItemList(Integer max, Integer circleId);

    /**
     * 设置置顶圈子
     *
     * @param circle
     * @return
     */
    Map<String, Object> setTop(Integer circle);

    /**
     * 获取圈子置顶数据
     *
     * @param max
     * @return
     */
    Map<String, Object> top(Integer max, Integer circleId);

    /**
     * 添加回复
     *
     * @param reply_type 回复主体类型
     * @param reply_id   回复主体id
     * @param account_id 回复人
     * @param content    回复内容
     * @return
     */
    Map<String, Object> addReply(Integer reply_type, Integer reply_id, Integer account_id, String content);


    /**
     * 获取圈子首推数据
     *
     * @return
     */
    List<Circle> queryCircleDeva();

    /**
     * 获取动态首推数据
     *
     * @return
     */
    List<Concern> queryConcernDeva();

    /**
     * 根据圈子id和用户id获取圈子相关数据
     *
     * @param circleId
     * @param accountId
     * @return 关注数，帖子数，是否关注等数据
     */
    Map<String, Object> findCircle(Integer circleId, Integer accountId);


    /**
     * 根据关注类型跟对应id查询关注列表
     *
     * @param concernId
     * @param concernType
     * @return
     */
    Map<String, Object> findMyConcernParams(Integer concernId, Integer concernType);

    /**
     * 根据用户id删除帖子
     *
     * @param createThisId
     * @param circleItemId
     * @return
     */
    Map<String, Object> deleteCircleItem(Integer createThisId, Integer circleItemId);

    /**
     * 取消圈主
     *
     * @param circleId
     * @param accountId
     * @return
     */
    Map<String, Object> closeMaster(Integer circleId, Integer accountId);

    /**
     * 修改圈子图片
     *
     * @param imgUrl
     * @param circleId
     * @return
     */
    Map<String, Object> updateCircleImg(String imgUrl, Integer circleId);

    /**
     * 设置管理员
     *
     * @param createId
     * @param adminIds
     * @param circleId
     * @return
     */
    Map<String, Object> setAdmins(Integer createId, String adminIds, Integer circleId);


}
