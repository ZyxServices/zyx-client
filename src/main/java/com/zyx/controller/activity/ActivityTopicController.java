package com.zyx.controller.activity;

import com.utils.FileUploadUtils;
import com.zyx.constants.Constants;
import com.zyx.constants.activity.ActivityConstants;
import com.zyx.entity.activity.parm.AddMemberInfoParm;
import com.zyx.entity.activity.parm.AddTopicParm;
import com.zyx.rpc.activity.ActivityTopicFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SubDong on 16-6-16.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title ActivityTopicController
 * @package com.zyx.controller.activity
 * @update 16-6-16 下午2:45
 */
@RestController
@RequestMapping("/v1/activity")
public class ActivityTopicController {

    @Resource
    private ActivityTopicFacade activityTopicFacade;

    @RequestMapping(value = "/dynamic", method = RequestMethod.POST)
    public ModelAndView dynamic(@RequestParam(name = "token", required = false) String token,
                                @RequestParam(name = "activityId", required = true) Integer activitiId,
                                @RequestParam(name = "userId", required = true) Integer userId,
                                @RequestParam(name = "topicTitle", required = true) String topicTitle,
                                @RequestParam(name = "topicContent", required = true) String topicContent,
                                @RequestParam(name = "image", required = true) MultipartFile[] image) {


        AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> map = new HashMap<>();

        if (image != null && image.length > 9) {
            map.put(Constants.STATE,ActivityConstants.AUTH_ERROR_10007);
            map.put(Constants.ERROR_MSG,"图片上传超过9张图片");
        }
        String images = "";
        if (image != null) {
            for (MultipartFile multipartFile : image) {
                images = images + FileUploadUtils.uploadFile(multipartFile) + ",";
            }
        }
        images = images.substring(0, images.length() - 1);

        AddTopicParm parm = new AddTopicParm();
        parm.setUserId(userId);
        parm.setActivityId(activitiId);
        parm.setImages(images);
        parm.setTopicTitle(topicTitle);
        parm.setTopicContent(topicContent);


        Map<String, Object> topic = activityTopicFacade.addActivityTopic(parm);

        jsonView.setAttributesMap(topic);
        return new ModelAndView(jsonView);
    }


}
